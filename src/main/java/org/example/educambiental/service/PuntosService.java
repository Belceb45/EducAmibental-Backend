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

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PuntosService {
    private final UsuarioRepository usuarioRepository;
    private final ModuloInteractivoRepository moduloInteractivoRepository;
    private final HistorialPuntosRepository historialPuntosRepository;

    @Transactional
    public void completarActividad(UUID idUsuario, Long idModulo) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + idUsuario));

        ModuloInteractivo modulo = moduloInteractivoRepository.findById(idModulo)
                .orElseThrow(() -> new ResourceNotFoundException("Módulo interactivo no encontrado con ID: " + idModulo));

        Integer puntosAOtorgar = modulo.getPuntosOtorgados();

        usuario.setPuntosActuales(usuario.getPuntosActuales() + puntosAOtorgar);
        usuarioRepository.save(usuario);

        HistorialPuntos historial = HistorialPuntos.builder()
                .usuario(usuario)
                .cantidad(puntosAOtorgar)
                .tipoOperacion("GANANCIA")
                .motivo("Completó el módulo: " + modulo.getTitulo())
                .build();
        
        historialPuntosRepository.save(historial);
    }
}
