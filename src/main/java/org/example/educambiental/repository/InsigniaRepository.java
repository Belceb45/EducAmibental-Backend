package org.example.educambiental.repository;

import org.example.educambiental.entity.Insignia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InsigniaRepository extends JpaRepository<Insignia, Long> {
    Optional<Insignia> findByNombre(String nombre);
}
