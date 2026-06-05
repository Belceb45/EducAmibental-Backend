package org.example.educambiental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "contenidos_estaticos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContenidoEstatico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo; // 'NOTICIA', 'TIP', 'FAQ'

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String cuerpo;

    private String autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor")
    private Usuario usuarioAutor;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;
}
