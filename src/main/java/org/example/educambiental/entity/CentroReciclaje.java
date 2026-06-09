package org.example.educambiental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String imagenUrl;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ManyToMany
    @JoinTable(
            name = "centro_materiales",
            joinColumns = @JoinColumn(name = "id_centro"),
            inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    private List<CategoriaResiduo> materialesAceptados;
}
