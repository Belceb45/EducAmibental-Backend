package org.example.educambiental.repository;

import org.example.educambiental.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID; // <-- Asegúrate de importar UUID

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> { // <-- Cambiar a UUID
    Optional<Usuario> findByCorreo(String correo);
}