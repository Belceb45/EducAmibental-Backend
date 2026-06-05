package org.example.educambiental.controller;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.CanjeRequestDto;
import org.example.educambiental.dto.CanjeResponseDto;
import org.example.educambiental.dto.RecompensaRequestDto;
import org.example.educambiental.dto.RecompensaResponseDto;
import org.example.educambiental.mapper.RecompensaMapper;
import org.example.educambiental.service.RecompensaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recompensas")
@RequiredArgsConstructor
public class RecompensaController {
    private final RecompensaService recompensaService;
    private final RecompensaMapper recompensaMapper;

    @GetMapping
    public Page<RecompensaResponseDto> listarRecompensas(Pageable pageable) {
        return recompensaService.listarTodas(pageable).map(recompensaMapper::toResponseDto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RecompensaResponseDto crearRecompensa(@Valid @RequestBody RecompensaRequestDto requestDto) {
        return recompensaMapper.toResponseDto(
                recompensaService.crearRecompensa(recompensaMapper.toEntity(requestDto))
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RecompensaResponseDto actualizarRecompensa(@PathVariable Long id, @Valid @RequestBody RecompensaRequestDto requestDto) {
        return recompensaMapper.toResponseDto(
                recompensaService.actualizarRecompensa(id, recompensaMapper.toEntity(requestDto))
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarRecompensa(@PathVariable Long id) {
        recompensaService.eliminarRecompensa(id);
    }

    @PostMapping("/canjear")
    public ResponseEntity<CanjeResponseDto> canjearRecompensa(@Valid @RequestBody CanjeRequestDto request) {
        CanjeResponseDto response = recompensaService.canjearRecompensa(request.getIdUsuario(), request.getIdRecompensa());
        return ResponseEntity.ok(response);
    }
}
