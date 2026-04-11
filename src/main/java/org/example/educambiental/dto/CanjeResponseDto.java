package org.example.educambiental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CanjeResponseDto {
    private String codigoAlfanumerico;
    private String mensaje;
    private Integer puntosRestantes;
}
