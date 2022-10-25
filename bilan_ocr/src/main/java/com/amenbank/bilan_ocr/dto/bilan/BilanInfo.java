package com.amenbank.bilan_ocr.dto.bilan;

import com.amenbank.bilan_ocr.entity.Bilan;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class BilanInfo {
    @NotBlank
    protected String matricule;
    @NotBlank
    protected String rs;
    @Pattern(regexp = "^\\d\\d\\d\\d$")
    protected int year;
    @NotBlank
    protected Bilan.EtatBilan etat;
}
