package org.example.educambiental.controller;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.ModuloInteractivoRequestDto;
import org.example.educambiental.dto.ModuloInteractivoResponseDto;
import org.example.educambiental.mapper.ModuloInteractivoMapper;
import org.example.educambiental.repository.ModuloInteractivoRepository;
import org.example.educambiental.entity.ModuloInteractivo;
import org.example.educambiental.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/modulos-educativos")
@RequiredArgsConstructor
public class ModuloInteractivoController {

    private final ModuloInteractivoRepository repository;
    private final ModuloInteractivoMapper mapper;

    @GetMapping
    public Page<ModuloInteractivoResponseDto> listarTodos(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponseDto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN_CONTENT')")
    public ModuloInteractivoResponseDto crear(@Valid @RequestBody ModuloInteractivoRequestDto request) {
        ModuloInteractivo entity = mapper.toEntity(request);
        return mapper.toResponseDto(repository.save(entity));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_CONTENT')")
    public ModuloInteractivoResponseDto actualizar(@PathVariable Long id, @Valid @RequestBody ModuloInteractivoRequestDto request) {
        ModuloInteractivo entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Módulo no encontrado"));
        
        entity.setTitulo(request.getTitulo());
        entity.setDescripcion(request.getDescripcion());
        entity.setContenido(request.getContenido());
        entity.setTipo(request.getTipo());
        entity.setPuntosOtorgados(request.getPuntosOtorgados());
        
        return mapper.toResponseDto(repository.save(entity));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_CONTENT')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Módulo no encontrado");
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
