package org.example.educambiental.repository;

import org.example.educambiental.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    Page<Material> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    List<Material> findByImagenUrlIsNotNull();
}
