package org.example.educambiental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialResponseDto {
    private Long id;
    private String nombre;
    private String instruccionesReciclaje;
    private CategoriaResiduoResponseDto categoria;
}
