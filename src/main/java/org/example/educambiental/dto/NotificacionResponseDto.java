package org.example.educambiental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionResponseDto {
    private Long id;
    private String mensaje;
    private Boolean leida;
    private LocalDateTime fechaCreacion;
}
