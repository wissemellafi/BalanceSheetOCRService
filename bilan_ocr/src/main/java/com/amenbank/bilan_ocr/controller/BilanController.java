package com.amenbank.bilan_ocr.controller;

import com.amenbank.bilan_ocr.dto.bilan.BilanDocumentDto;
import com.amenbank.bilan_ocr.dto.bilan.BilanDto;
import com.amenbank.bilan_ocr.dto.bilan.BilanResponse;
import com.amenbank.bilan_ocr.dto.page.PageDto;
import com.amenbank.bilan_ocr.entity.Bilan;
import com.amenbank.bilan_ocr.service.IBilanService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bilans")
public class BilanController {
    private final IBilanService bilanService;
    private final ModelMapper modelMapper;

    public BilanController(IBilanService bilanService, ModelMapper modelMapper) {
        this.bilanService = bilanService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<PageDto<BilanResponse>> getBilans(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        var bilansPage = bilanService.findAll(PageRequest.of(page, size));

        var bilans = bilansPage.getContent().stream()
                .map(bilan -> modelMapper.map(bilan, BilanResponse.class))
                .collect(Collectors.toList());

        var pageData = new PageDto<>(bilansPage.getTotalElements(), bilans);

        return ResponseEntity.ok(pageData);
    }

    @GetMapping("{matricule}")
    public BilanResponse getBilan(@PathVariable String matricule) {
        var bilan = bilanService.findByMatricule(matricule);

        return modelMapper.map(bilan, BilanResponse.class);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BilanResponse uploadBilan(@ModelAttribute BilanDocumentDto bilanInfo) throws JsonProcessingException {
        var bilan = bilanService.save(bilanInfo);

        return modelMapper.map(bilan, BilanResponse.class);
    }

    @PutMapping("{matricule}")
    public BilanResponse updateBilan(@PathVariable String matricule, @RequestBody BilanDto bilanRequest) {
        bilanRequest.setMatricule(matricule);
        var bilan = modelMapper.map(bilanRequest, Bilan.class);
        var bilanRes =  bilanService.update(bilan);

        return modelMapper.map(bilanRes, BilanResponse.class);
    }

    @DeleteMapping("{matricule}")
    public void deleteBilan(@PathVariable String matricule) {
        bilanService.deleteByMatricule(matricule);
    }
}
