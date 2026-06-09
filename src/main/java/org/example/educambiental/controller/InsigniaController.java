package org.example.educambiental.controller;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.InsigniaResponseDto;
import org.example.educambiental.service.InsigniaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/insignias")
@RequiredArgsConstructor
public class InsigniaController {

    private final InsigniaService insigniaService;

    @GetMapping
    public List<InsigniaResponseDto> listarCatalogo() {
        return insigniaService.listarCatalogo();
    }

    @GetMapping("/usuario/{idUsuario}")
    @PreAuthorize("#idUsuario == authentication.principal.id or hasRole('ADMIN_SYSTEM')")
    public List<InsigniaResponseDto> listarPorUsuario(@PathVariable UUID idUsuario) {
        return insigniaService.listarPorUsuario(idUsuario);
    }
}
