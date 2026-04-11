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
public class CentroReciclajeRequestDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private Double latitud;
    private Double longitud;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    private String horario;
    private String contacto;
}
