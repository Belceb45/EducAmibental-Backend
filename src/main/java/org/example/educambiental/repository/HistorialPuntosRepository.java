package org.example.educambiental.repository;

import org.example.educambiental.entity.HistorialPuntos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID; // <-- Importar UUID

@Repository
public interface HistorialPuntosRepository extends JpaRepository<HistorialPuntos, Long> {
    // IMPORTANTE: Cambiar el tipo de parámetro a UUID
    List<HistorialPuntos> findByUsuarioId(UUID idUsuario);
}