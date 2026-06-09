package org.example.educambiental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialPuntosResponseDto {
    private Long id;
    private Integer cantidad;
    private String tipoOperacion;
    private String motivo;
    private LocalDateTime fecha;
}
