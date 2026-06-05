package org.example.educambiental.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class DashboardResponseDto {
    private String saludo;
    private Integer puntosActuales;
    private String nivelActual;
    private ContenidoResponseDto tipDelDia;
    private List<ContenidoResponseDto> articulosRecientes;
    private List<CategoriaResiduoResponseDto> guiasPorCategoria;
}
