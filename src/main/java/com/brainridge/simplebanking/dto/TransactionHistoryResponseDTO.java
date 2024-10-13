package com.brainridge.simplebanking.dto;

import com.brainridge.simplebanking.constants.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class TransactionHistoryResponseDTO {

    BigDecimal amount;

    BigDecimal balance;

    TransactionType transactionType;

    String counterPartyEmail;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timeStamp;
}
