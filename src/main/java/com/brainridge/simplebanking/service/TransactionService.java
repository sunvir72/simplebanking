package com.brainridge.simplebanking.service;

import com.brainridge.simplebanking.constants.TransactionType;
import com.brainridge.simplebanking.dto.TransactionHistoryRequestDTO;
import com.brainridge.simplebanking.dto.TransactionHistoryResponseDTO;
import com.brainridge.simplebanking.dto.TransferFundsRequestDTO;
import com.brainridge.simplebanking.dto.TransferFundsResponseDTO;
import com.brainridge.simplebanking.exception.TransactionHistoryException;
import com.brainridge.simplebanking.exception.TransferFundsException;
import com.brainridge.simplebanking.model.Account;
import com.brainridge.simplebanking.model.Transaction;
import com.brainridge.simplebanking.repository.TransactionRepository;
import com.brainridge.simplebanking.service.validators.IValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final IValidationService<TransactionHistoryRequestDTO> transactionHistoryValidationService;
    private final IValidationService<TransferFundsRequestDTO> transferFundsValidationService;

    @Autowired
    TransactionService(TransactionRepository transactionRepository,
                       AccountService accountService,
                       IValidationService<TransactionHistoryRequestDTO> transactionHistoryValidationService,
                       IValidationService<TransferFundsRequestDTO> transferFundsValidationService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.transactionHistoryValidationService = transactionHistoryValidationService;
        this.transferFundsValidationService = transferFundsValidationService;
    }

    @Transactional
    public TransferFundsResponseDTO transferFunds(@RequestBody TransferFundsRequestDTO request) {
        List<String> errors = transferFundsValidationService.validate(request);
        if (!errors.isEmpty()) {
            throw new TransferFundsException(errors);
        }
        BigDecimal amount = request.getAmount();
        Account senderAccount = accountService.debitAmount(request.getSenderAccountId(), amount);
        Account receiverAccount = accountService.creditAmount(request.getReceiverAccountId(), amount);
        Transaction senderTransaction = new Transaction(senderAccount.getId(),
                TransactionType.DEBIT, amount, senderAccount.getBalance(), receiverAccount.getEmail());
        transactionRepository.save(senderTransaction);
        Transaction receiverTransaction = new Transaction(receiverAccount.getId(),
                TransactionType.CREDIT, amount, receiverAccount.getBalance(), senderAccount.getEmail());
        transactionRepository.save(receiverTransaction);
        return TransferFundsResponseDTO.builder()
                .createdAt(senderTransaction.getCreatedAt())
                .receiverEmail(receiverAccount.getEmail())
                .amount(request.getAmount())
                .build();
    }

    @Transactional(readOnly = true)
    public List<TransactionHistoryResponseDTO> getTransactionHistory(@RequestBody TransactionHistoryRequestDTO request) {
        List<String> errors = transactionHistoryValidationService.validate(request);
        if (errors.isEmpty()) {
            List<Transaction> transactions = transactionRepository.findByAccountId(request.getAccountId());
            return transactions.stream()
                    .map(transaction -> TransactionHistoryResponseDTO.builder()
                            .amount(transaction.getAmount())
                            .balance(transaction.getBalance())
                            .transactionType(transaction.getTransactionType())
                            .counterPartyEmail(transaction.getCounterPartyEmail())
                            .timeStamp(transaction.getCreatedAt())
                            .build())
                    .collect(Collectors.toList());
        }
        throw new TransactionHistoryException(errors);
    }
}
