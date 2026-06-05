package org.example.educambiental.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ScannerResponseDto {
    private String nombreProducto;
    private String imagenUrl;
    private List<String> materialesDetectados;
    private String instruccionesSugeridas;
    private String categoriaSugerida;
    private boolean encontrado;
}
