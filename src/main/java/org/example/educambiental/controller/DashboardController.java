package org.example.educambiental.controller;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.DashboardResponseDto;
import org.example.educambiental.dto.UsuarioResponseDto;
import org.example.educambiental.service.ContenidoEstaticoService;
import org.example.educambiental.service.UsuarioService;
import org.example.educambiental.service.MaterialService;
import org.example.educambiental.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ContenidoEstaticoService contenidoService;
    private final UsuarioService usuarioService;

    @GetMapping("/inicio")
    public ResponseEntity<DashboardResponseDto> getInicio() {
        // En una app real, obtendríamos el usuario del SecurityContext
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        // Por ahora simulamos o buscamos un usuario base si es necesario
        
        return ResponseEntity.ok(DashboardResponseDto.builder()
                .saludo("¡Hola de nuevo!")
                .puntosActuales(100)
                .nivelActual("Eco-Aprendiz")
                .tipDelDia(contenidoService.getTipDelDia())
                .articulosRecientes(contenidoService.getContenidoPorTipo("ARTICULO"))
                .guiasPorCategoria(Collections.emptyList()) // Se llenaría con las categorías de la DB
                .build());
    }
}
