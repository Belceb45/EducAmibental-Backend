package org.example.educambiental.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.educambiental.entity.TicketPrioridad;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRequestDto {
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    private TicketPrioridad prioridad;
}
