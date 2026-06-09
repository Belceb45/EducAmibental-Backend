package org.example.educambiental.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuloInteractivoRequestDto {
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    private String descripcion;
    private String contenido;
    private String tipo;

    @NotNull(message = "Los puntos otorgados son obligatorios")
    @Min(value = 0, message = "Los puntos no pueden ser negativos")
    private Integer puntosOtorgados;
}
