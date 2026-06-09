package org.example.educambiental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "modulos_interactivos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuloInteractivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    private String tipo; // "TRIVIA", "CURSO", "VIDEO", etc.

    @Column(name = "puntos_otorgados", nullable = false)
    private Integer puntosOtorgados;

    @ManyToMany
    @JoinTable(
            name = "usuario_modulo",
            joinColumns = @JoinColumn(name = "id_modulo"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private List<Usuario> usuariosCompletados;
}
