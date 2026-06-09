package org.example.educambiental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDashboardResponseDto {
    private long totalUsuarios;
    private long usuariosVerificados;
    private long totalCentros;
    private long totalMateriales;
    private long totalContenidos;
    private long ticketsAbiertos;
    /** Conteo de tickets agrupado por estado (ABIERTO, EN_PROGRESO, RESUELTO, CERRADO). */
    private Map<String, Long> ticketsPorEstado;
}
