package org.example.educambiental.service;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.CanjeResponseDto;
import org.example.educambiental.entity.CodigoDescuento;
import org.example.educambiental.entity.HistorialPuntos;
import org.example.educambiental.entity.Recompensa;
import org.example.educambiental.entity.Usuario;
import org.example.educambiental.exception.ResourceNotFoundException;
import org.example.educambiental.repository.CodigoDescuentoRepository;
import org.example.educambiental.repository.HistorialPuntosRepository;
import org.example.educambiental.repository.RecompensaRepository;
import org.example.educambiental.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecompensaService {
    private final UsuarioRepository usuarioRepository;
    private final RecompensaRepository recompensaRepository;
    private final CodigoDescuentoRepository codigoDescuentoRepository;
    private final HistorialPuntosRepository historialPuntosRepository;

    public Page<Recompensa> listarTodas(Pageable pageable) {
        return recompensaRepository.findAll(pageable);
    }

    public Recompensa crearRecompensa(Recompensa recompensa) {
        return recompensaRepository.save(recompensa);
    }

    @Transactional
    public CanjeResponseDto canjearRecompensa(UUID idUsuario, Long idRecompensa) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + idUsuario));

        Recompensa recompensa = recompensaRepository.findById(idRecompensa)
                .orElseThrow(() -> new ResourceNotFoundException("Recompensa no encontrada con ID: " + idRecompensa));

        if (usuario.getPuntosActuales() < recompensa.getCostoPuntos()) {
            throw new RuntimeException("Puntos insuficientes para canjear esta recompensa.");
        }

        CodigoDescuento codigo = codigoDescuentoRepository.findFirstByRecompensaAndEstado(recompensa, "DISPONIBLE")
                .orElseThrow(() -> new RuntimeException("No hay stock disponible para esta recompensa."));

        usuario.setPuntosActuales(usuario.getPuntosActuales() - recompensa.getCostoPuntos());
        usuarioRepository.save(usuario);

        codigo.setEstado("CANJEADO");
        codigo.setUsuario(usuario);
        codigoDescuentoRepository.save(codigo);

        recompensa.setStock(recompensa.getStock() - 1);
        recompensaRepository.save(recompensa);

        HistorialPuntos historial = HistorialPuntos.builder()
                .usuario(usuario)
                .cantidad(recompensa.getCostoPuntos())
                .tipoOperacion("CANJE")
                .motivo("Canje de recompensa: " + recompensa.getDescripcion())
                .build();
        historialPuntosRepository.save(historial);

        return CanjeResponseDto.builder()
                .codigoAlfanumerico(codigo.getCodigoAlfanumerico())
                .mensaje("¡Canje realizado con éxito!")
                .puntosRestantes(usuario.getPuntosActuales())
                .build();
    }
}
