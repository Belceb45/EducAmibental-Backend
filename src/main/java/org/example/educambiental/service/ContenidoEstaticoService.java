package org.example.educambiental.service;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.ContenidoResponseDto;
import org.example.educambiental.entity.ContenidoEstatico;
import org.example.educambiental.entity.Material;
import org.example.educambiental.repository.ContenidoEstaticoRepository;
import org.example.educambiental.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.example.educambiental.exception.ResourceNotFoundException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContenidoEstaticoService {

    private final ContenidoEstaticoRepository repository;
    private final MaterialRepository materialRepository;

    public List<ContenidoResponseDto> getContenidoPorTipo(String tipo) {
        Map<Long, String> imagenPorCategoria = construirImagenPorCategoria();
        return repository.findByTipo(tipo).stream()
                .map(c -> mapToDto(c, imagenPorCategoria))
                .collect(Collectors.toList());
    }

    public ContenidoResponseDto getTipDelDia() {
        Map<Long, String> imagenPorCategoria = construirImagenPorCategoria();
        return repository.findByTipo("TIP").stream()
                .findFirst()
                .map(c -> mapToDto(c, imagenPorCategoria))
                .orElse(null);
    }

    public ContenidoResponseDto crearContenido(ContenidoEstatico contenido) {
        contenido.setFechaPublicacion(LocalDateTime.now());
        return mapToDto(repository.save(contenido), construirImagenPorCategoria());
    }

    public void eliminarContenido(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Contenido no encontrado.");
        }
        repository.deleteById(id);
    }

    public ContenidoResponseDto actualizarContenido(Long id, ContenidoEstatico detalles) {
        ContenidoEstatico contenido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contenido no encontrado."));

        contenido.setTitulo(detalles.getTitulo());
        contenido.setCuerpo(detalles.getCuerpo());
        contenido.setTipo(detalles.getTipo());
        contenido.setAutor(detalles.getAutor());
        contenido.setCategoria(detalles.getCategoria());

        return mapToDto(repository.save(contenido), construirImagenPorCategoria());
    }

    /**
     * Mapa categoríaId -> imagen_url, tomando el primer material de cada categoría
     * que tenga imagen definida. Así una guía reutiliza la imagen alusiva del
     * material de su misma categoría (p. ej. la guía de plásticos usa la imagen
     * del material de plásticos).
     */
    private Map<Long, String> construirImagenPorCategoria() {
        Map<Long, String> imagenPorCategoria = new HashMap<>();
        for (Material material : materialRepository.findByImagenUrlIsNotNull()) {
            if (material.getCategoria() != null) {
                imagenPorCategoria.putIfAbsent(material.getCategoria().getId(), material.getImagenUrl());
            }
        }
        return imagenPorCategoria;
    }

    private ContenidoResponseDto mapToDto(ContenidoEstatico c, Map<Long, String> imagenPorCategoria) {
        Long categoriaId = null;
        String categoriaNombre = null;
        String imagenUrl = null;
        if (c.getCategoria() != null) {
            categoriaId = c.getCategoria().getId();
            categoriaNombre = c.getCategoria().getNombre();
            imagenUrl = imagenPorCategoria.get(categoriaId);
        }

        return ContenidoResponseDto.builder()
                .id(c.getId())
                .titulo(c.getTitulo())
                .cuerpo(c.getCuerpo())
                .tipo(c.getTipo())
                .autor(c.getAutor() != null ? c.getAutor() : "Admin")
                .idCategoria(categoriaId)
                .categoria(categoriaNombre)
                .imagenUrl(imagenUrl)
                .build();
    }
}
