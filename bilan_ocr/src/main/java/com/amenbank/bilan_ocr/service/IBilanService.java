package com.amenbank.bilan_ocr.service;

import com.amenbank.bilan_ocr.dto.bilan.BilanDocumentDto;
import com.amenbank.bilan_ocr.entity.Bilan;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBilanService {
    Page<Bilan> findAll(Pageable page);
    Bilan findByMatricule(String matricule);
    Bilan save(BilanDocumentDto bilanInfo) throws JsonProcessingException;
    Bilan update(Bilan bilan);
    void deleteByMatricule(String matricule);
}
