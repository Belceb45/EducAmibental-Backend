package org.example.educambiental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecompensaResponseDto {
    private Long id;
    private String tiendaFicticia;
    private String descripcion;
    private Integer costoPuntos;
    private Integer stock;
}
