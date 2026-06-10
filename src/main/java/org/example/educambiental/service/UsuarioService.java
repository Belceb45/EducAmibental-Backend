package org.example.educambiental.service;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.RankingEntryDto;
import org.example.educambiental.entity.AuthProvider;
import org.example.educambiental.entity.Usuario;
import org.example.educambiental.exception.ResourceNotFoundException;
import org.example.educambiental.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Set<String> ROLES_ADMIN = Set.of("ADMIN_SYSTEM", "ADMIN_CONTENT");

    public Page<Usuario> listarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    /** Ranking comunitario (RF17): top usuarios por puntos, con su posición. */
    public List<RankingEntryDto> obtenerRanking(int top) {
        List<Usuario> usuarios = usuarioRepository.findAllByOrderByPuntosActualesDesc(PageRequest.of(0, top));
        List<RankingEntryDto> ranking = new ArrayList<>();
        int posicion = 1;
        for (Usuario u : usuarios) {
            ranking.add(RankingEntryDto.builder()
                    .posicion(posicion++)
                    .idUsuario(u.getId())
                    .nombre(u.getNombre())
                    .puntosActuales(u.getPuntosActuales())
                    .nivelActual(u.getNivelActual())
                    .build());
        }
        return ranking;
    }

    public Usuario crearUsuario(Usuario usuario) {
        // Por defecto, nuevos usuarios son "USER" a menos que se especifique lo contrario
        if (usuario.getRol() == null) {
            usuario.setRol("USER");
        }
        return usuarioRepository.save(usuario);
    }

    /**
     * Crea una cuenta administrativa (ADMIN_SYSTEM o ADMIN_CONTENT). Solo debe
     * invocarse desde un endpoint protegido para ADMIN_SYSTEM. La contraseña se
     * cifra con BCrypt y la cuenta queda activa (sin verificación por correo).
     */
    @Transactional
    public Usuario crearAdmin(String nombre, String correo, String password, String rol) {
        if (rol == null || !ROLES_ADMIN.contains(rol)) {
            throw new RuntimeException("Rol inválido. Debe ser ADMIN_SYSTEM o ADMIN_CONTENT.");
        }
        if (usuarioRepository.existsByCorreo(correo)) {
            throw new RuntimeException("El correo electrónico ya está registrado.");
        }
        Usuario admin = Usuario.builder()
                .nombre(nombre)
                .correo(correo)
                .password(passwordEncoder.encode(password))
                .rol(rol)
                .provider(AuthProvider.LOCAL)
                .puntosActuales(0)
                .nivelActual(1)
                .enabled(true)
                .build();
        return usuarioRepository.save(admin);
    }

    public Usuario obtenerPorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }

    @Transactional
    public void eliminarUsuario(UUID idUsuarioAEliminar) {
        if (!usuarioRepository.existsById(idUsuarioAEliminar)) {
            throw new ResourceNotFoundException("Usuario a eliminar no encontrado.");
        }

        usuarioRepository.deleteById(idUsuarioAEliminar);
    }

    @Transactional
    public void eliminarUsuarioAutenticado() {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario autenticado no encontrado."));
        
        usuarioRepository.delete(usuario);
    }
}
