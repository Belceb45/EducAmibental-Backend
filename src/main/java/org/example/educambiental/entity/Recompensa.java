package org.example.educambiental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recompensas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recompensa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tienda_ficticia")
    private String tiendaFicticia;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "costo_puntos", nullable = false)
    private Integer costoPuntos;

    @Column(nullable = false)
    private Integer stock;
}
