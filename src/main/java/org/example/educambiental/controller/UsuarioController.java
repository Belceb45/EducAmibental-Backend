package org.example.educambiental.controller;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.CrearAdminRequestDto;
import org.example.educambiental.dto.ImpactoResponseDto;
import org.example.educambiental.dto.RankingEntryDto;
import org.example.educambiental.dto.UsuarioRequestDto;
import org.example.educambiental.dto.UsuarioResponseDto;
import org.example.educambiental.mapper.UsuarioMapper;
import org.example.educambiental.service.GamificacionService;
import org.example.educambiental.service.PuntosService;
import org.example.educambiental.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final PuntosService puntosService;
    private final GamificacionService gamificacionService;
    private final UsuarioMapper usuarioMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public Page<UsuarioResponseDto> listarUsuarios(Pageable pageable) {
        return usuarioService.listarTodos(pageable).map(usuarioMapper::toResponseDto);
    }

    @PostMapping
    public UsuarioResponseDto crearUsuario(@Valid @RequestBody UsuarioRequestDto requestDto) {
        return usuarioMapper.toResponseDto(
                usuarioService.crearUsuario(usuarioMapper.toEntity(requestDto))
        );
    }

    /** Permite a un Admin de Sistema crear cuentas de administrador (RF4). */
    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public UsuarioResponseDto crearAdmin(@Valid @RequestBody CrearAdminRequestDto requestDto) {
        return usuarioMapper.toResponseDto(
                usuarioService.crearAdmin(
                        requestDto.getNombre(),
                        requestDto.getCorreo(),
                        requestDto.getPassword(),
                        requestDto.getRol()
                )
        );
    }

    @PostMapping("/{idUsuario}/completar-actividad/{idModulo}")
    @PreAuthorize("#idUsuario == authentication.principal.id")
    public ResponseEntity<Map<String, String>> completarActividad(
            @PathVariable UUID idUsuario,
            @PathVariable Long idModulo) {
        puntosService.completarActividad(idUsuario, idModulo);
        return ResponseEntity.ok(Map.of("message", "Actividad completada y puntos otorgados."));
    }

    @GetMapping("/ranking")
    public List<RankingEntryDto> obtenerRanking() {
        return usuarioService.obtenerRanking(20);
    }

    @GetMapping("/{idUsuario}/impacto")
    @PreAuthorize("#idUsuario == authentication.principal.id or hasRole('ADMIN_SYSTEM')")
    public ImpactoResponseDto obtenerImpacto(@PathVariable UUID idUsuario) {
        return gamificacionService.construirImpacto(idUsuario);
    }

    @DeleteMapping("/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN_SYSTEM') or #idUsuario == authentication.principal.id")
    public ResponseEntity<Map<String, String>> eliminarUsuario(@PathVariable UUID idUsuario) {
        usuarioService.eliminarUsuario(idUsuario);
        return ResponseEntity.ok(Map.of("message", "Usuario eliminado exitosamente."));
    }

    @DeleteMapping("/mi-cuenta")
    public ResponseEntity<Map<String, String>> eliminarMiCuenta(@RequestHeader("Authorization") String token) {
        // En un escenario real, extraeríamos el ID del token JWT. 
        // Para simplificar y alinearnos con la estructura actual:
        usuarioService.eliminarUsuarioAutenticado();
        return ResponseEntity.ok(Map.of("message", "Tu cuenta ha sido eliminada exitosamente."));
    }
}
