package org.example.educambiental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImpactoResponseDto {
    private Integer puntosActuales;
    private Integer nivelActual;
    private String etiquetaNivel;
    /** XP acumulado dentro del nivel actual (0..xpParaSiguiente). */
    private Integer xpEnNivel;
    /** XP necesario para completar el nivel actual y subir al siguiente. */
    private Integer xpParaSiguiente;
    private Integer totalModulosCompletados;
    private List<InsigniaResponseDto> insignias;
    private List<HistorialPuntosResponseDto> historialReciente;
}
