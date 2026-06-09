package org.example.educambiental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingEntryDto {
    private Integer posicion;
    private UUID idUsuario;
    private String nombre;
    private Integer puntosActuales;
    private Integer nivelActual;
}
