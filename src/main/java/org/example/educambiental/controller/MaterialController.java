package org.example.educambiental.controller;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.MaterialRequestDto;
import org.example.educambiental.dto.MaterialResponseDto;
import org.example.educambiental.mapper.MaterialMapper;
import org.example.educambiental.service.MaterialService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/materiales")
@RequiredArgsConstructor
public class MaterialController {
    private final MaterialService materialService;
    private final MaterialMapper materialMapper;

    @GetMapping
    public Page<MaterialResponseDto> listarMateriales(Pageable pageable) {
        return materialService.listarTodos(pageable).map(materialMapper::toResponseDto);
    }

    @GetMapping("/buscar")
    public Page<MaterialResponseDto> buscarMateriales(@RequestParam String nombre, Pageable pageable) {
        return materialService.buscarPorNombre(nombre, pageable).map(materialMapper::toResponseDto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN_CONTENT')")
    public MaterialResponseDto crearMaterial(@Valid @RequestBody MaterialRequestDto requestDto) {
        return materialMapper.toResponseDto(
                materialService.crearMaterial(materialMapper.toEntity(requestDto))
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_CONTENT')")
    public MaterialResponseDto actualizarMaterial(@PathVariable Long id, @Valid @RequestBody MaterialRequestDto requestDto) {
        return materialMapper.toResponseDto(
                materialService.actualizarMaterial(id, materialMapper.toEntity(requestDto))
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_CONTENT')")
    public void eliminarMaterial(@PathVariable Long id) {
        materialService.eliminarMaterial(id);
    }
}
