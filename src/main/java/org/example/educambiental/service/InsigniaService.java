package org.example.educambiental.service;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.InsigniaResponseDto;
import org.example.educambiental.entity.Insignia;
import org.example.educambiental.entity.Usuario;
import org.example.educambiental.exception.ResourceNotFoundException;
import org.example.educambiental.repository.InsigniaRepository;
import org.example.educambiental.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsigniaService {
    private final InsigniaRepository insigniaRepository;
    private final UsuarioRepository usuarioRepository;

    /** Catálogo completo de insignias (RF15). */
    public List<InsigniaResponseDto> listarCatalogo() {
        return insigniaRepository.findAll().stream()
                .map(i -> toDto(i, null))
                .collect(Collectors.toList());
    }

    /** Insignias con la marca {@code obtenida} para un usuario concreto. */
    @Transactional(readOnly = true)
    public List<InsigniaResponseDto> listarPorUsuario(UUID idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + idUsuario));
        Set<Long> obtenidas = obtenidasIds(usuario);
        return insigniaRepository.findAll().stream()
                .map(i -> toDto(i, obtenidas.contains(i.getId())))
                .collect(Collectors.toList());
    }

    /**
     * Otorga una insignia (por nombre) a un usuario de forma idempotente.
     * @return true si la insignia se otorgó por primera vez; false si ya la tenía o no existe en el catálogo.
     */
    @Transactional
    public boolean otorgar(Usuario usuario, String nombreInsignia) {
        Insignia insignia = insigniaRepository.findByNombre(nombreInsignia).orElse(null);
        if (insignia == null) {
            return false; // El catálogo aún no contiene esta insignia: no es un error.
        }
        if (insignia.getUsuariosConInsignia() == null) {
            insignia.setUsuariosConInsignia(new ArrayList<>());
        }
        boolean yaLaTiene = insignia.getUsuariosConInsignia().stream()
                .anyMatch(u -> u.getId().equals(usuario.getId()));
        if (yaLaTiene) {
            return false;
        }
        insignia.getUsuariosConInsignia().add(usuario);
        insigniaRepository.save(insignia);
        return true;
    }

    @Transactional(readOnly = true)
    public List<InsigniaResponseDto> insigniasObtenidas(Usuario usuario) {
        Set<Long> obtenidas = obtenidasIds(usuario);
        return insigniaRepository.findAll().stream()
                .filter(i -> obtenidas.contains(i.getId()))
                .map(i -> toDto(i, true))
                .collect(Collectors.toList());
    }

    private Set<Long> obtenidasIds(Usuario usuario) {
        List<Insignia> ganadas = usuario.getInsigniasGanadas();
        if (ganadas == null) {
            return Set.of();
        }
        return ganadas.stream().map(Insignia::getId).collect(Collectors.toSet());
    }

    private InsigniaResponseDto toDto(Insignia i, Boolean obtenida) {
        return InsigniaResponseDto.builder()
                .id(i.getId())
                .nombre(i.getNombre())
                .descripcion(i.getDescripcion())
                .iconoUrl(i.getIconoUrl())
                .obtenida(obtenida)
                .build();
    }
}
