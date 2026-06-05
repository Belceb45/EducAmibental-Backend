package org.example.educambiental.service;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.ContenidoResponseDto;
import org.example.educambiental.entity.ContenidoEstatico;
import org.example.educambiental.repository.ContenidoEstaticoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.example.educambiental.exception.ResourceNotFoundException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContenidoEstaticoService {

    private final ContenidoEstaticoRepository repository;

    public List<ContenidoResponseDto> getContenidoPorTipo(String tipo) {
        return repository.findByTipo(tipo).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ContenidoResponseDto getTipDelDia() {
        return repository.findByTipo("TIP").stream()
                .findFirst()
                .map(this::mapToDto)
                .orElse(null);
    }

    public ContenidoResponseDto crearContenido(ContenidoEstatico contenido) {
        contenido.setFechaPublicacion(LocalDateTime.now());
        return mapToDto(repository.save(contenido));
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
        
        return mapToDto(repository.save(contenido));
    }

    private ContenidoResponseDto mapToDto(ContenidoEstatico c) {
        return ContenidoResponseDto.builder()
                .id(c.getId())
                .titulo(c.getTitulo())
                .cuerpo(c.getCuerpo())
                .tipo(c.getTipo())
                .autor(c.getAutor() != null ? c.getAutor() : "Admin")
                .build();
    }
}
