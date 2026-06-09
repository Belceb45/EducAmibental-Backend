package org.example.educambiental.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaResiduoRequestDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;
    private String instruccionesGenerales;
    private String icono;
    private String color;
}
