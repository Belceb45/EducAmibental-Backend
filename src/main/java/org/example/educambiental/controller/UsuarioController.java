package org.example.educambiental.controller;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.UsuarioRequestDto;
import org.example.educambiental.dto.UsuarioResponseDto;
import org.example.educambiental.mapper.UsuarioMapper;
import org.example.educambiental.service.PuntosService;
import org.example.educambiental.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final PuntosService puntosService;
    private final UsuarioMapper usuarioMapper;

    @GetMapping
    public Page<UsuarioResponseDto> listarUsuarios(Pageable pageable) {
        return usuarioService.listarTodos(pageable).map(usuarioMapper::toResponseDto);
    }

    @PostMapping
    public UsuarioResponseDto crearUsuario(@Valid @RequestBody UsuarioRequestDto requestDto) {
        return usuarioMapper.toResponseDto(
                usuarioService.crearUsuario(usuarioMapper.toEntity(requestDto))
        );
    }

    @PostMapping("/{idUsuario}/completar-actividad/{idModulo}")
    public ResponseEntity<String> completarActividad(
            @PathVariable UUID idUsuario,
            @PathVariable Long idModulo) {
        puntosService.completarActividad(idUsuario, idModulo);
        return ResponseEntity.ok("Actividad completada y puntos otorgados.");
    }

    @DeleteMapping("/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN') or #idUsuario == authentication.principal.id")
    public ResponseEntity<String> eliminarUsuario(@PathVariable UUID idUsuario) {
        usuarioService.eliminarUsuario(idUsuario);
        return ResponseEntity.ok("Usuario eliminado exitosamente.");
    }

    @DeleteMapping("/mi-cuenta")
    public ResponseEntity<String> eliminarMiCuenta(@RequestHeader("Authorization") String token) {
        // En un escenario real, extraeríamos el ID del token JWT. 
        // Para simplificar y alinearnos con la estructura actual:
        usuarioService.eliminarUsuarioAutenticado();
        return ResponseEntity.ok("Tu cuenta ha sido eliminada exitosamente.");
    }
}
