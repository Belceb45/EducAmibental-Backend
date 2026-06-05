package org.example.educambiental.controller;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.ContenidoResponseDto;
import org.example.educambiental.service.ContenidoEstaticoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.example.educambiental.dto.ContenidoRequestDto;
import org.example.educambiental.entity.ContenidoEstatico;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/contenido")
@RequiredArgsConstructor
public class ContenidoEstaticoController {

    private final ContenidoEstaticoService contenidoService;

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ContenidoResponseDto>> getPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(contenidoService.getContenidoPorTipo(tipo.toUpperCase()));
    }

    @GetMapping("/tip-dia")
    public ResponseEntity<ContenidoResponseDto> getTipDia() {
        ContenidoResponseDto tip = contenidoService.getTipDelDia();
        return tip != null ? ResponseEntity.ok(tip) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContenidoResponseDto> crear(@Valid @RequestBody ContenidoRequestDto request) {
        ContenidoEstatico entity = ContenidoEstatico.builder()
                .titulo(request.getTitulo())
                .cuerpo(request.getCuerpo())
                .tipo(request.getTipo())
                .autor(request.getAutor())
                .build();
        return ResponseEntity.ok(contenidoService.crearContenido(entity));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContenidoResponseDto> actualizar(@PathVariable Long id, @Valid @RequestBody ContenidoRequestDto request) {
        ContenidoEstatico entity = ContenidoEstatico.builder()
                .titulo(request.getTitulo())
                .cuerpo(request.getCuerpo())
                .tipo(request.getTipo())
                .autor(request.getAutor())
                .build();
        return ResponseEntity.ok(contenidoService.actualizarContenido(id, entity));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        contenidoService.eliminarContenido(id);
        return ResponseEntity.noContent().build();
    }
}
