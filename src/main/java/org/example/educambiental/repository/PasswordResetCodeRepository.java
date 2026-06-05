package org.example.educambiental.repository;

import org.example.educambiental.entity.PasswordResetCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetCodeRepository extends JpaRepository<PasswordResetCode, Long> {
    Optional<PasswordResetCode> findByEmailAndCode(String email, String code);
    void deleteByEmail(String email);
}
