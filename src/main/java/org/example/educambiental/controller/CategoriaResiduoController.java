package org.example.educambiental.controller;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.CategoriaResiduoRequestDto;
import org.example.educambiental.dto.CategoriaResiduoResponseDto;
import org.example.educambiental.mapper.CategoriaResiduoMapper;
import org.example.educambiental.service.MaterialService; // Suponiendo que la lógica de categorías está aquí o crear un servicio aparte
import org.example.educambiental.repository.CategoriaResiduoRepository;
import org.example.educambiental.entity.CategoriaResiduo;
import org.example.educambiental.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaResiduoController {

    private final CategoriaResiduoRepository repository;
    private final CategoriaResiduoMapper mapper;

    @GetMapping
    public List<CategoriaResiduoResponseDto> listarTodas() {
        return repository.findAll().stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN_CONTENT')")
    public CategoriaResiduoResponseDto crear(@Valid @RequestBody CategoriaResiduoRequestDto request) {
        CategoriaResiduo entity = CategoriaResiduo.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .instruccionesGenerales(request.getInstruccionesGenerales())
                .icono(request.getIcono())
                .color(request.getColor())
                .build();
        return mapper.toResponseDto(repository.save(entity));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_CONTENT')")
    public CategoriaResiduoResponseDto actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaResiduoRequestDto request) {
        CategoriaResiduo entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setInstruccionesGenerales(request.getInstruccionesGenerales());
        entity.setIcono(request.getIcono());
        entity.setColor(request.getColor());
        
        return mapper.toResponseDto(repository.save(entity));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_CONTENT')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada");
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
