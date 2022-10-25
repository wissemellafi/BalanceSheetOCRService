package com.amenbank.bilan_ocr.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUser {
    private String username;
    private String role;
    private String token;
    private long expiresIn;
}
