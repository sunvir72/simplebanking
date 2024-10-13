package com.brainridge.simplebanking.service.validators;

import com.brainridge.simplebanking.dto.TransferFundsRequestDTO;
import com.brainridge.simplebanking.model.Account;
import com.brainridge.simplebanking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TransferFundsValidator implements ICustomValidator<TransferFundsRequestDTO> {

    private final AccountRepository accountRepository;

    @Autowired
    public TransferFundsValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<String> validate(TransferFundsRequestDTO request) {
        List<String> errors = new ArrayList<>();
        if(request.getSenderAccountId().equals(request.getReceiverAccountId())){
            errors.add("Sender and Receiver must be different");
        }
        if (accountRepository.findById(request.getReceiverAccountId()).isEmpty()) {
            errors.add("Receiver not found");
        }
        Optional<Account> senderAccount = accountRepository.findById(request.getSenderAccountId());
        if (senderAccount.isEmpty()) {
            errors.add("Sender not found");
        } else if (senderAccount.get().getBalance().subtract(request.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
            errors.add("Insufficient balance");
        }
        return errors;
    }
}
