package org.example.educambiental.controller;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.dto.TicketRequestDto;
import org.example.educambiental.dto.TicketResponseDto;
import org.example.educambiental.entity.TicketEstado;
import org.example.educambiental.mapper.TicketMapper;
import org.example.educambiental.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public Page<TicketResponseDto> listarTodos(Pageable pageable) {
        return ticketService.listarTodos(pageable).map(ticketMapper::toResponseDto);
    }

    @GetMapping("/mis-tickets/{idUsuario}")
    @PreAuthorize("hasRole('USER') and #idUsuario == authentication.principal.id or hasRole('ADMIN_SYSTEM')")
    public Page<TicketResponseDto> listarPorUsuario(@PathVariable UUID idUsuario, Pageable pageable) {
        return ticketService.listarPorUsuario(idUsuario, pageable).map(ticketMapper::toResponseDto);
    }

    @PostMapping("/usuario/{idUsuario}")
    @PreAuthorize("hasRole('USER') and #idUsuario == authentication.principal.id")
    public TicketResponseDto crearTicket(@PathVariable UUID idUsuario, @Valid @RequestBody TicketRequestDto requestDto) {
        return ticketMapper.toResponseDto(
                ticketService.crearTicket(ticketMapper.toEntity(requestDto), idUsuario)
        );
    }

    @PatchMapping("/{idTicket}/asignar/{idAdmin}")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public TicketResponseDto asignarAdmin(@PathVariable UUID idTicket, @PathVariable UUID idAdmin) {
        return ticketMapper.toResponseDto(ticketService.asignarAdmin(idTicket, idAdmin));
    }

    @PatchMapping("/{idTicket}/estado")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public TicketResponseDto cambiarEstado(@PathVariable UUID idTicket, @RequestParam TicketEstado nuevoEstado) {
        return ticketMapper.toResponseDto(ticketService.cambiarEstado(idTicket, nuevoEstado));
    }
}
