package org.example.educambiental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String correo;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(50) default 'LOCAL'")
    @Builder.Default
    private AuthProvider provider = AuthProvider.LOCAL;

    @Column(name = "puntos_actuales", nullable = false, columnDefinition = "integer default 0")
    @Builder.Default
    private Integer puntosActuales = 0;

    @Column(name = "nivel_actual", nullable = false, columnDefinition = "integer default 1")
    @Builder.Default
    private Integer nivelActual = 1;

    @Column(nullable = false)
    @Builder.Default
    private String rol = "USER"; // "USER", "ADMIN_SYSTEM", "ADMIN_CONTENT"

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CodigoDescuento> codigosDescuento;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistorialPuntos> historialPuntos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notificacion> notificaciones;

    @OneToMany(mappedBy = "autor")
    private List<ContenidoEstatico> contenidosPublicados;

    @ManyToMany(mappedBy = "usuariosCompletados")
    private List<ModuloInteractivo> modulosCompletados;

    @ManyToMany(mappedBy = "usuariosConInsignia")
    private List<Insignia> insigniasGanadas;

    @Column(nullable = false)
    @Builder.Default
    private boolean enabled = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol));
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
