package com.amenbank.bilan_ocr.dto.bilan;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BilanResponse extends BilanDto {
    private LocalDate createdAt;
    private LocalDate lastModifiedAt;
    private String publisher;
    private String modifier;
}
