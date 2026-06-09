package org.example.educambiental.service;

import lombok.RequiredArgsConstructor;
import org.example.educambiental.entity.Ticket;
import org.example.educambiental.entity.TicketEstado;
import org.example.educambiental.entity.Usuario;
import org.example.educambiental.exception.ResourceNotFoundException;
import org.example.educambiental.repository.TicketRepository;
import org.example.educambiental.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;

    public Page<Ticket> listarTodos(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    public Page<Ticket> listarPorUsuario(UUID idUsuario, Pageable pageable) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return ticketRepository.findByUsuarioReporta(usuario, pageable);
    }

    @Transactional
    public Ticket crearTicket(Ticket ticket, UUID idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        ticket.setUsuarioReporta(usuario);
        ticket.setEstado(TicketEstado.ABIERTO);
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket asignarAdmin(UUID idTicket, UUID idAdmin) {
        Ticket ticket = ticketRepository.findById(idTicket)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
        Usuario admin = usuarioRepository.findById(idAdmin)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador no encontrado"));

        if (!"ADMIN_SYSTEM".equals(admin.getRol())) {
            throw new RuntimeException("Solo un Administrador de Sistema puede ser asignado a un ticket");
        }

        ticket.setAdminAsignado(admin);
        ticket.setEstado(TicketEstado.EN_PROGRESO);
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket cambiarEstado(UUID idTicket, TicketEstado nuevoEstado) {
        Ticket ticket = ticketRepository.findById(idTicket)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
        ticket.setEstado(nuevoEstado);
        return ticketRepository.save(ticket);
    }
}
