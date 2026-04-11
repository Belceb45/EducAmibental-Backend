package org.example.educambiental.repository;

import org.example.educambiental.entity.ModuloInteractivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuloInteractivoRepository extends JpaRepository<ModuloInteractivo, Long> {
}
