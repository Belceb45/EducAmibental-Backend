package org.example.educambiental.repository;

import org.example.educambiental.entity.Usuario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID; // <-- Asegúrate de importar UUID

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> { // <-- Cambiar a UUID
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);

    /** Ranking comunitario (RF17): usuarios ordenados por puntos descendente. */
    List<Usuario> findAllByOrderByPuntosActualesDesc(Pageable pageable);

    /** Métricas admin (RF19): cuántas cuentas están verificadas/activas. */
    long countByEnabledTrue();
}