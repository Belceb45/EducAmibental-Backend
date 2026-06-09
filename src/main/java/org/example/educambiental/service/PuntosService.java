package org.example.educambiental.service;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.entity.HistorialPuntos;
import org.example.educambiental.entity.ModuloInteractivo;
import org.example.educambiental.entity.Usuario;
import org.example.educambiental.exception.ResourceNotFoundException;
import org.example.educambiental.repository.HistorialPuntosRepository;
import org.example.educambiental.repository.ModuloInteractivoRepository;
import org.example.educambiental.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PuntosService {
    private final UsuarioRepository usuarioRepository;
    private final ModuloInteractivoRepository moduloInteractivoRepository;
    private final HistorialPuntosRepository historialPuntosRepository;
    private final NotificacionService notificacionService;
    private final GamificacionService gamificacionService;

    @Transactional
    public void completarActividad(UUID idUsuario, Long idModulo) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + idUsuario));

        ModuloInteractivo modulo = moduloInteractivoRepository.findById(idModulo)
                .orElseThrow(() -> new ResourceNotFoundException("Módulo interactivo no encontrado con ID: " + idModulo));

        // Idempotencia: no se permite recompletar el mismo módulo (evita farmeo de XP).
        if (modulo.getUsuariosCompletados() == null) {
            modulo.setUsuariosCompletados(new ArrayList<>());
        }
        boolean yaCompletado = modulo.getUsuariosCompletados().stream()
                .anyMatch(u -> u.getId().equals(usuario.getId()));
        if (yaCompletado) {
            throw new IllegalStateException("Ya completaste este módulo anteriormente.");
        }

        Integer puntosAOtorgar = modulo.getPuntosOtorgados();
        int nivelAnterior = usuario.getNivelActual() != null ? usuario.getNivelActual() : 1;

        // Registrar la finalización del módulo.
        modulo.getUsuariosCompletados().add(usuario);
        moduloInteractivoRepository.save(modulo);

        // Otorgar XP y recalcular el nivel.
        int nuevosPuntos = usuario.getPuntosActuales() + puntosAOtorgar;
        usuario.setPuntosActuales(nuevosPuntos);
        usuario.setNivelActual(GamificacionService.calcularNivel(nuevosPuntos));
        usuarioRepository.save(usuario);

        // Historial de puntos.
        HistorialPuntos historial = HistorialPuntos.builder()
                .usuario(usuario)
                .cantidad(puntosAOtorgar)
                .tipoOperacion("GANANCIA")
                .motivo("Completó el módulo: " + modulo.getTitulo())
                .build();
        historialPuntosRepository.save(historial);

        // Notificar la ganancia y evaluar subidas de nivel / insignias.
        notificacionService.crear(usuario,
                "✅ Completaste \"" + modulo.getTitulo() + "\" y ganaste " + puntosAOtorgar + " XP.");
        gamificacionService.evaluarLogros(usuario, nivelAnterior);
    }
}
