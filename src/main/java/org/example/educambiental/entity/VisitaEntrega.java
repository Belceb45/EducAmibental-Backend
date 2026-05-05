package org.example.educambiental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "visitas_entregas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitaEntrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_centro", nullable = false)
    private CentroReciclaje centroReciclaje;

    @Column(name = "kilogramos_reciclados", nullable = false)
    private Double kilogramosReciclados;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;
}
