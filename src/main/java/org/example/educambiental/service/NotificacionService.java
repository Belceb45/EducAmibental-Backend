package org.example.educambiental.service;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.entity.Notificacion;
import org.example.educambiental.entity.Usuario;
import org.example.educambiental.exception.ResourceNotFoundException;
import org.example.educambiental.repository.NotificacionRepository;
import org.example.educambiental.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificacionService {
    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;

    /** Crea y persiste una notificación para un usuario (usado por la gamificación y los canjes). */
    @Transactional
    public Notificacion crear(Usuario usuario, String mensaje) {
        Notificacion notificacion = Notificacion.builder()
                .usuario(usuario)
                .mensaje(mensaje)
                .leida(false)
                .fechaCreacion(LocalDateTime.now())
                .build();
        return notificacionRepository.save(notificacion);
    }

    public List<Notificacion> listarPorUsuario(UUID idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        return notificacionRepository.findByUsuarioOrderByFechaCreacionDesc(usuario);
    }

    public long contarNoLeidas(UUID idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        return notificacionRepository.countByUsuarioAndLeidaFalse(usuario);
    }

    @Transactional
    public Notificacion marcarLeida(UUID idUsuario, Long idNotificacion) {
        Usuario usuario = obtenerUsuario(idUsuario);
        Notificacion notificacion = notificacionRepository.findByIdAndUsuario(idNotificacion, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada"));
        notificacion.setLeida(true);
        return notificacionRepository.save(notificacion);
    }

    @Transactional
    public void marcarTodasLeidas(UUID idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        List<Notificacion> noLeidas = notificacionRepository.findByUsuarioAndLeidaFalse(usuario);
        noLeidas.forEach(n -> n.setLeida(true));
        notificacionRepository.saveAll(noLeidas);
    }

    private Usuario obtenerUsuario(UUID idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + idUsuario));
    }
}
