package org.example.educambiental.repository;

import org.example.educambiental.entity.CentroReciclaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroReciclajeRepository extends JpaRepository<CentroReciclaje, Long> {
}
