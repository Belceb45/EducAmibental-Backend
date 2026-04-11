package org.example.educambiental.repository;

import org.example.educambiental.entity.CodigoDescuento;
import org.example.educambiental.entity.Recompensa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodigoDescuentoRepository extends JpaRepository<CodigoDescuento, Long> {
    Optional<CodigoDescuento> findFirstByRecompensaAndEstado(Recompensa recompensa, String estado);
}
