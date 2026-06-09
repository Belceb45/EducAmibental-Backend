package org.example.educambiental.repository;

import org.example.educambiental.entity.Notificacion;
import org.example.educambiental.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByUsuarioOrderByFechaCreacionDesc(Usuario usuario);
    long countByUsuarioAndLeidaFalse(Usuario usuario);
    Optional<Notificacion> findByIdAndUsuario(Long id, Usuario usuario);
    List<Notificacion> findByUsuarioAndLeidaFalse(Usuario usuario);
}
