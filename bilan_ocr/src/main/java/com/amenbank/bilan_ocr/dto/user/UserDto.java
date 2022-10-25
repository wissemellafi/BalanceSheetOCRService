package com.amenbank.bilan_ocr.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    @NotBlank
    protected String username;
    @NotBlank
    protected String firstName;
    @NotBlank
    protected String lastName;
    @NotBlank
    protected String role;
    @NotNull
    protected boolean enabled;
}
