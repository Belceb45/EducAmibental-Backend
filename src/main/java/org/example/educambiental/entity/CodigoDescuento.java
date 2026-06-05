package org.example.educambiental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "codigos_descuento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodigoDescuento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recompensa", nullable = false)
    private Recompensa recompensa;

    @Column(name = "codigo_alfanumerico", unique = true, nullable = false)
    private String codigoAlfanumerico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(nullable = false, columnDefinition = "varchar(50) default 'DISPONIBLE'")
    @Builder.Default
    private String estado = "DISPONIBLE"; // "DISPONIBLE" o "CANJEADO"
}
