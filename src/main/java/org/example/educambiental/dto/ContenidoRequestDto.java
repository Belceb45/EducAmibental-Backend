package org.example.educambiental.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContenidoRequestDto {
    @NotBlank
    private String titulo;
    
    @NotBlank
    private String cuerpo;
    
    @NotBlank
    private String tipo; // TIP, ARTICULO, GUIA

    private String autor;

    // Categoría de residuo asociada (opcional). Para guías define la imagen mostrada.
    private Long idCategoria;
}
