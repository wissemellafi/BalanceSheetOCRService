package com.amenbank.bilan_ocr.repository;

import com.amenbank.bilan_ocr.entity.Bilan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BilanRepository extends JpaRepository<Bilan, String> {
    boolean existsByMatricule(String matricule);
}
