package com.amenbank.bilan_ocr.dto.bilan;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class BilanDocumentDto extends BilanInfo {
    @NotNull
    private MultipartFile document;
}
