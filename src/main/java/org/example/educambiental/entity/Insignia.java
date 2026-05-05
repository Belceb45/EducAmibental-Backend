package org.example.educambiental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "insignias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Insignia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "icono_url")
    private String iconoUrl;

    @ManyToMany
    @JoinTable(
            name = "usuario_insignia",
            joinColumns = @JoinColumn(name = "id_insignia"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private List<Usuario> usuariosConInsignia;
}
