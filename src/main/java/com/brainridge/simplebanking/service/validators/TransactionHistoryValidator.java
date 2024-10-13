package com.brainridge.simplebanking.service.validators;

import com.brainridge.simplebanking.dto.TransactionHistoryRequestDTO;
import com.brainridge.simplebanking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionHistoryValidator implements ICustomValidator<TransactionHistoryRequestDTO> {

    AccountRepository accountRepository;

    @Autowired
    public TransactionHistoryValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<String> validate(TransactionHistoryRequestDTO request) {
        List<String> errors = new ArrayList<>();
        Long accountId = request.getAccountId();
        if (accountRepository.findById(accountId).isEmpty()) {
            errors.add("Account not found");
        }
        return errors;
    }
}
