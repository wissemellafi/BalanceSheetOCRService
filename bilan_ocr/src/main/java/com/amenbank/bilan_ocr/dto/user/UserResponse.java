package com.amenbank.bilan_ocr.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserResponse extends UserDto {
    private Integer id;
    private LocalDate createdAt;
}
