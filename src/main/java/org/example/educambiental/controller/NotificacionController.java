package org.example.educambiental.controller;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.NotificacionResponseDto;
import org.example.educambiental.mapper.NotificacionMapper;
import org.example.educambiental.service.NotificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;
    private final NotificacionMapper notificacionMapper;

    @GetMapping("/usuario/{idUsuario}")
    @PreAuthorize("#idUsuario == authentication.principal.id or hasRole('ADMIN_SYSTEM')")
    public List<NotificacionResponseDto> listar(@PathVariable UUID idUsuario) {
        return notificacionService.listarPorUsuario(idUsuario).stream()
                .map(notificacionMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/usuario/{idUsuario}/no-leidas/count")
    @PreAuthorize("#idUsuario == authentication.principal.id or hasRole('ADMIN_SYSTEM')")
    public Map<String, Long> contarNoLeidas(@PathVariable UUID idUsuario) {
        return Map.of("noLeidas", notificacionService.contarNoLeidas(idUsuario));
    }

    @PatchMapping("/{idNotificacion}/leer")
    @PreAuthorize("#idUsuario == authentication.principal.id or hasRole('ADMIN_SYSTEM')")
    public NotificacionResponseDto marcarLeida(
            @PathVariable Long idNotificacion,
            @RequestParam UUID idUsuario) {
        return notificacionMapper.toResponseDto(
                notificacionService.marcarLeida(idUsuario, idNotificacion));
    }

    @PatchMapping("/usuario/{idUsuario}/leer-todas")
    @PreAuthorize("#idUsuario == authentication.principal.id or hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<Map<String, String>> marcarTodasLeidas(@PathVariable UUID idUsuario) {
        notificacionService.marcarTodasLeidas(idUsuario);
        return ResponseEntity.ok(Map.of("message", "Todas las notificaciones fueron marcadas como leídas."));
    }
}
