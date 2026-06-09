package org.example.educambiental.service;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.HistorialPuntosResponseDto;
import org.example.educambiental.dto.ImpactoResponseDto;
import org.example.educambiental.dto.InsigniaResponseDto;
import org.example.educambiental.entity.HistorialPuntos;
import org.example.educambiental.entity.Usuario;
import org.example.educambiental.exception.ResourceNotFoundException;
import org.example.educambiental.repository.HistorialPuntosRepository;
import org.example.educambiental.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Centraliza la lógica de gamificación (RF14/RF15): cálculo de niveles a partir del XP,
 * otorgamiento de insignias, emisión de notificaciones de logro y construcción del panel de impacto.
 *
 * Modelo de niveles: cada nivel requiere {@link #XP_POR_NIVEL} puntos de XP.
 * nivel = puntosAcumulados / XP_POR_NIVEL + 1 (el nivel mínimo es 1).
 */
@Service
@RequiredArgsConstructor
public class GamificacionService {

    public static final int XP_POR_NIVEL = 1000;

    private static final String INSIGNIA_PRIMER_MODULO = "Primer Módulo";
    private static final String INSIGNIA_APRENDIZ = "Aprendiz Verde";
    private static final String INSIGNIA_MAESTRO = "Eco-Maestro";
    private static final String INSIGNIA_COLECCIONISTA = "Coleccionista de XP";

    private final InsigniaService insigniaService;
    private final NotificacionService notificacionService;
    private final HistorialPuntosRepository historialPuntosRepository;
    private final UsuarioRepository usuarioRepository;

    // ─── Cálculo de niveles (utilidades estáticas, sin estado) ──────────────────

    public static int calcularNivel(int puntosAcumulados) {
        if (puntosAcumulados < 0) puntosAcumulados = 0;
        return puntosAcumulados / XP_POR_NIVEL + 1;
    }

    public static int xpEnNivel(int puntosAcumulados) {
        if (puntosAcumulados < 0) puntosAcumulados = 0;
        return puntosAcumulados % XP_POR_NIVEL;
    }

    public static String etiquetaNivel(int nivel) {
        return switch (Math.max(nivel, 1)) {
            case 1 -> "Eco-Aprendiz";
            case 2 -> "Eco-Explorador";
            case 3 -> "Eco-Guardián";
            case 4 -> "Eco-Experto";
            default -> "Eco-Maestro";
        };
    }

    // ─── Evaluación de logros tras ganar XP ─────────────────────────────────────

    /**
     * Evalúa subidas de nivel e insignias después de que el usuario gane XP.
     * Debe invocarse dentro de una transacción con el usuario ya actualizado y persistido.
     */
    @Transactional
    public void evaluarLogros(Usuario usuario, int nivelAnterior) {
        int nivelActual = usuario.getNivelActual() != null ? usuario.getNivelActual() : 1;
        if (nivelActual > nivelAnterior) {
            notificacionService.crear(usuario,
                    "¡Subiste al nivel " + nivelActual + " (" + etiquetaNivel(nivelActual) + ")! Sigue aprendiendo.");
        }

        int modulosCompletados = usuario.getModulosCompletados() != null ? usuario.getModulosCompletados().size() : 0;
        int puntos = usuario.getPuntosActuales() != null ? usuario.getPuntosActuales() : 0;

        if (modulosCompletados >= 1) {
            otorgarYNotificar(usuario, INSIGNIA_PRIMER_MODULO);
        }
        if (nivelActual >= 2) {
            otorgarYNotificar(usuario, INSIGNIA_APRENDIZ);
        }
        if (nivelActual >= 5) {
            otorgarYNotificar(usuario, INSIGNIA_MAESTRO);
        }
        if (puntos >= 1000) {
            otorgarYNotificar(usuario, INSIGNIA_COLECCIONISTA);
        }
    }

    private void otorgarYNotificar(Usuario usuario, String nombreInsignia) {
        if (insigniaService.otorgar(usuario, nombreInsignia)) {
            notificacionService.crear(usuario, "🏅 ¡Has desbloqueado la insignia \"" + nombreInsignia + "\"!");
        }
    }

    // ─── Panel de impacto (RF15) ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public ImpactoResponseDto construirImpacto(UUID idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + idUsuario));

        int puntos = usuario.getPuntosActuales() != null ? usuario.getPuntosActuales() : 0;
        int nivel = usuario.getNivelActual() != null ? usuario.getNivelActual() : calcularNivel(puntos);
        int totalModulos = usuario.getModulosCompletados() != null ? usuario.getModulosCompletados().size() : 0;

        List<InsigniaResponseDto> insignias = insigniaService.listarPorUsuario(idUsuario);

        List<HistorialPuntosResponseDto> historial = historialPuntosRepository.findByUsuarioId(idUsuario).stream()
                .sorted(Comparator.comparing(HistorialPuntos::getFecha,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(10)
                .map(this::toHistorialDto)
                .collect(Collectors.toList());

        return ImpactoResponseDto.builder()
                .puntosActuales(puntos)
                .nivelActual(nivel)
                .etiquetaNivel(etiquetaNivel(nivel))
                .xpEnNivel(xpEnNivel(puntos))
                .xpParaSiguiente(XP_POR_NIVEL)
                .totalModulosCompletados(totalModulos)
                .insignias(insignias)
                .historialReciente(historial)
                .build();
    }

    private HistorialPuntosResponseDto toHistorialDto(HistorialPuntos h) {
        return HistorialPuntosResponseDto.builder()
                .id(h.getId())
                .cantidad(h.getCantidad())
                .tipoOperacion(h.getTipoOperacion())
                .motivo(h.getMotivo())
                .fecha(h.getFecha())
                .build();
    }
}
