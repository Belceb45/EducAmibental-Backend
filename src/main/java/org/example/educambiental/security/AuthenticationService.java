package org.example.educambiental.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.UsuarioRequestDto;
import org.example.educambiental.entity.AuthProvider;
import org.example.educambiental.entity.Usuario;
import org.example.educambiental.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import org.example.educambiental.entity.PasswordResetCode;
import org.example.educambiental.entity.VerificationCode;
import org.example.educambiental.repository.PasswordResetCodeRepository;
import org.example.educambiental.repository.VerificationCodeRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsuarioRepository repository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final PasswordResetCodeRepository passwordResetCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender mailSender;

    @Value("${google.client.id}")
    private String googleClientId;

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        Usuario user = repository.findByCorreo(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (user.getProvider() != AuthProvider.LOCAL) {
            throw new RuntimeException("La recuperación de contraseña solo está disponible para cuentas locales");
        }

        String code = generateVerificationCode();
        savePasswordResetCode(request.getEmail(), code);
        sendResetEmail(request.getEmail(), code);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetCode resetCode = passwordResetCodeRepository
                .findByEmailAndCode(request.getEmail(), request.getCode())
                .orElseThrow(() -> new RuntimeException("Código de recuperación inválido"));

        if (resetCode.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El código de recuperación ha expirado");
        }

        Usuario user = repository.findByCorreo(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);

        passwordResetCodeRepository.deleteByEmail(request.getEmail());
    }

    private void savePasswordResetCode(String email, String code) {
        passwordResetCodeRepository.deleteByEmail(email);
        PasswordResetCode prc = PasswordResetCode.builder()
                .email(email)
                .code(code)
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .build();
        passwordResetCodeRepository.save(prc);
    }

    private void sendResetEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Recuperación de contraseña - EducAmbiental");
        message.setText("Tu código para restablecer tu contraseña es: " + code + "\nExpira en 15 minutos.");
        mailSender.send(message);
    }

    @Transactional
    public AuthenticationResponse register(UsuarioRequestDto request) {
        if (repository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }
        var user = Usuario.builder()
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol("USER")
                .provider(AuthProvider.LOCAL)
                .puntosActuales(0)
                .enabled(false) // Disabled until verified
                .build();
        repository.save(user);

        String code = generateVerificationCode();
        saveVerificationCode(request.getCorreo(), code);
        sendVerificationEmail(request.getCorreo(), code);

        return AuthenticationResponse.builder()
                .token(null) // Do not return token until verified
                .build();
    }

    @Transactional
    public AuthenticationResponse verify(VerificationRequest request) {
        VerificationCode verificationCode = verificationCodeRepository
                .findByEmailAndCode(request.getEmail(), request.getCode())
                .orElseThrow(() -> new RuntimeException("Código de verificación inválido"));

        if (verificationCode.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El código de verificación ha expirado");
        }

        Usuario user = repository.findByCorreo(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setEnabled(true);
        repository.save(user);

        verificationCodeRepository.deleteByEmail(request.getEmail());

        var jwtToken = jwtService.generateToken(user);
        return mapToAuthenticationResponse(user, jwtToken);
    }

    private AuthenticationResponse mapToAuthenticationResponse(Usuario user, String token) {
        return AuthenticationResponse.builder()
                .token(token)
                .id(user.getId())
                .nombre(user.getNombre())
                .correo(user.getCorreo())
                .rol(user.getRol())
                .puntosActuales(user.getPuntosActuales())
                .nivelActual(user.getNivelActual())
                .build();
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private void saveVerificationCode(String email, String code) {
        verificationCodeRepository.deleteByEmail(email);
        VerificationCode vc = VerificationCode.builder()
                .email(email)
                .code(code)
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .build();
        verificationCodeRepository.save(vc);
    }

    private void sendVerificationEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Código de verificación - EducAmbiental");
        message.setText("Tu código de verificación es: " + code + "\nExpira en 15 minutos.");
        mailSender.send(message);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByCorreo(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return mapToAuthenticationResponse(user, jwtToken);
    }

    public AuthenticationResponse authenticateWithGoogle(GoogleTokenRequest request) {
        try {
            NetHttpTransport transport = new NetHttpTransport();
            GsonFactory jsonFactory = GsonFactory.getDefaultInstance();

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(request.getIdToken());
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String email = payload.getEmail();
                String name = (String) payload.get("name");

                Usuario user = repository.findByCorreo(email)
                        .orElseGet(() -> {
                            Usuario newUser = Usuario.builder()
                                    .nombre(name != null ? name : email)
                                    .correo(email)
                                    .provider(AuthProvider.GOOGLE)
                                    .rol("USER")
                                    .puntosActuales(0)
                                    .nivelActual(1)
                                    .build();
                            return repository.save(newUser);
                        });

                // Si el usuario existía pero era LOCAL, podrías decidir vincularlo o lanzar error.
                // Aquí simplemente lo autenticamos si el correo coincide.

                var jwtToken = jwtService.generateToken(user);
                return mapToAuthenticationResponse(user, jwtToken);
            } else {
                throw new RuntimeException("Token de Google inválido");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al autenticar con Google: " + e.getMessage());
        }
    }
}
