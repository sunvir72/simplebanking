package com.brainridge.simplebanking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TransactionHistoryRequestDTO {
    @NotNull
    private Long accountId;
}
