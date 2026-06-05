package org.example.educambiental.repository;

import org.example.educambiental.entity.ContenidoEstatico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenidoEstaticoRepository extends JpaRepository<ContenidoEstatico, Long> {
    List<ContenidoEstatico> findByTipo(String tipo);
}
