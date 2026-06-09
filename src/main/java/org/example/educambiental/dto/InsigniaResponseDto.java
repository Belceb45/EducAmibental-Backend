package org.example.educambiental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsigniaResponseDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private String iconoUrl;
    /** Solo se rellena al consultar las insignias de un usuario concreto. */
    private Boolean obtenida;
}
