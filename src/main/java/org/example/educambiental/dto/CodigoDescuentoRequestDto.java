package org.example.educambiental.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodigoDescuentoRequestDto {
    @NotNull(message = "La recompensa es obligatoria")
    private Long idRecompensa;

    @NotBlank(message = "El código alfanumérico es obligatorio")
    private String codigoAlfanumerico;

    private UUID idUsuario;

    @Builder.Default
    private String estado = "DISPONIBLE";
}
