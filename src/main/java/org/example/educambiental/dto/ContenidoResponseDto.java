package org.example.educambiental.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContenidoResponseDto {
    private Long id;
    private String titulo;
    private String cuerpo;
    private String tipo; // TIP, ARTICULO, GUIA
    private String autor;
    private String imagenUrl;
}
