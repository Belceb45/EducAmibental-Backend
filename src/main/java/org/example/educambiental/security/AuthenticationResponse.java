package org.example.educambiental.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private UUID id;
    private String nombre;
    private String correo;
    private String rol;
    private Integer puntosActuales;
    private Integer nivelActual;
}
