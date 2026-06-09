package org.example.educambiental.security;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.UsuarioRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UsuarioRequestDto request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<AuthenticationResponse> verify(
            @RequestBody VerificationRequest request
    ) {
        return ResponseEntity.ok(service.verify(request));
    }

    @PostMapping("/google")
    public ResponseEntity<AuthenticationResponse> googleAuth(
            @RequestBody GoogleTokenRequest request
    ) {
        return ResponseEntity.ok(service.authenticateWithGoogle(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request
    ) {
        service.forgotPassword(request);
        return ResponseEntity.ok(Map.of("message", "Código de recuperación enviado a tu correo."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
    ) {
        service.resetPassword(request);
        return ResponseEntity.ok(Map.of("message", "Contraseña restablecida exitosamente."));
    }
}
