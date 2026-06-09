package org.example.educambiental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.educambiental.entity.TicketEstado;
import org.example.educambiental.entity.TicketPrioridad;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponseDto {
    private UUID id;
    private String titulo;
    private String descripcion;
    private TicketEstado estado;
    private TicketPrioridad prioridad;
    private String nombreUsuarioReporta;
    private String nombreAdminAsignado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
