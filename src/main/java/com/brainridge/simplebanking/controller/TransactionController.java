package com.brainridge.simplebanking.controller;

import com.brainridge.simplebanking.dto.TransactionHistoryRequestDTO;
import com.brainridge.simplebanking.dto.TransactionHistoryResponseDTO;
import com.brainridge.simplebanking.dto.TransferFundsRequestDTO;
import com.brainridge.simplebanking.dto.TransferFundsResponseDTO;
import com.brainridge.simplebanking.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.brainridge.simplebanking.constants.urls.TransactionURLs.TRANSACTION_HISTORY_URL;
import static com.brainridge.simplebanking.constants.urls.TransactionURLs.TRANSFER_FUNDS_URL;

@RestController
@RequestMapping
@Validated
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(TRANSFER_FUNDS_URL)
    public ResponseEntity<TransferFundsResponseDTO> transferFunds(@Valid @RequestBody TransferFundsRequestDTO request) {
        TransferFundsResponseDTO response = transactionService.transferFunds(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(TRANSACTION_HISTORY_URL)
    public ResponseEntity<List<TransactionHistoryResponseDTO>> getTransactionHistory(@Valid @RequestBody TransactionHistoryRequestDTO request) {
        List<TransactionHistoryResponseDTO> response = transactionService.getTransactionHistory(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
