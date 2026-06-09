package org.example.educambiental.controller;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.AdminDashboardResponseDto;
import org.example.educambiental.dto.CategoriaResiduoResponseDto;
import org.example.educambiental.dto.DashboardResponseDto;
import org.example.educambiental.entity.Usuario;
import org.example.educambiental.exception.ResourceNotFoundException;
import org.example.educambiental.mapper.CategoriaResiduoMapper;
import org.example.educambiental.repository.CategoriaResiduoRepository;
import org.example.educambiental.repository.UsuarioRepository;
import org.example.educambiental.service.ContenidoEstaticoService;
import org.example.educambiental.service.DashboardService;
import org.example.educambiental.service.GamificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ContenidoEstaticoService contenidoService;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaResiduoRepository categoriaRepository;
    private final CategoriaResiduoMapper categoriaMapper;
    private final DashboardService dashboardService;

    @GetMapping("/inicio")
    public ResponseEntity<DashboardResponseDto> getInicio() {
        // Obtenemos el usuario autenticado real desde el contexto de seguridad.
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByCorreo(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario autenticado no encontrado."));

        List<CategoriaResiduoResponseDto> guias = categoriaRepository.findAll().stream()
                .map(categoriaMapper::toResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(DashboardResponseDto.builder()
                .saludo("¡Hola de nuevo, " + primerNombre(usuario.getNombre()) + "!")
                .puntosActuales(usuario.getPuntosActuales())
                .nivelActual(GamificacionService.etiquetaNivel(usuario.getNivelActual()))
                .tipDelDia(contenidoService.getTipDelDia())
                .articulosRecientes(contenidoService.getContenidoPorTipo("ARTICULO"))
                .guiasPorCategoria(guias)
                .build());
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<AdminDashboardResponseDto> getAdminMetrics() {
        return ResponseEntity.ok(dashboardService.obtenerMetricasAdmin());
    }

    private String primerNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) return "Eco Amigo";
        return nombre.trim().split("\\s+")[0];
    }
}
