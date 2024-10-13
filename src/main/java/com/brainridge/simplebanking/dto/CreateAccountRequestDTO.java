package com.brainridge.simplebanking.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CreateAccountRequestDTO {
    @NotBlank
    @Email
    private String email;

    @DecimalMin(value = "0")
    private BigDecimal balance;
}
