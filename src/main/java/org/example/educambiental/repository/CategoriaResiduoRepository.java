package org.example.educambiental.repository;

import org.example.educambiental.entity.CategoriaResiduo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaResiduoRepository extends JpaRepository<CategoriaResiduo, Long> {
}
