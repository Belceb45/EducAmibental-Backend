package org.example.educambiental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias_residuo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaResiduo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(name = "instrucciones_generales", columnDefinition = "TEXT")
    private String instruccionesGenerales;

    private String icono;
    private String color;
}
