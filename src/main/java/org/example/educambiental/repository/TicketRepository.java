package org.example.educambiental.repository;

import org.example.educambiental.entity.Ticket;
import org.example.educambiental.entity.TicketEstado;
import org.example.educambiental.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    Page<Ticket> findByUsuarioReporta(Usuario usuario, Pageable pageable);
    Page<Ticket> findByEstado(TicketEstado estado, Pageable pageable);
    Page<Ticket> findByAdminAsignado(Usuario admin, Pageable pageable);
    long countByEstado(TicketEstado estado);
}
