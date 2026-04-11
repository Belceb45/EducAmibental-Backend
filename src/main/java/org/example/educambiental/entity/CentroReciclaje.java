package org.example.educambiental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "centros_reciclaje")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CentroReciclaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private Double latitud;
    private Double longitud;
    private String direccion;
    private String horario;
    private String contacto;
}
