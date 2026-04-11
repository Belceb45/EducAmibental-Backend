package org.example.educambiental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodigoDescuentoResponseDto {
    private Long id;
    private RecompensaResponseDto recompensa;
    private String codigoAlfanumerico;
    private UsuarioResponseDto usuario;
    private String estado;
}
