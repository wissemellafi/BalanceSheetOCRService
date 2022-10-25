package com.amenbank.bilan_ocr.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserRequest extends UserDto {
    @NotBlank
    private String password;
}
