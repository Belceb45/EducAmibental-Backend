package org.example.educambiental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuloInteractivoResponseDto {
    private Long id;
    private String titulo;
    private String descripcion;
    private String contenido;
    private String tipo;
    private Integer puntosOtorgados;
}
