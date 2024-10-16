package com.brainridge.simplebanking.service;

import com.brainridge.simplebanking.dto.CreateAccountRequestDTO;
import com.brainridge.simplebanking.dto.CreateAccountResponseDTO;
import com.brainridge.simplebanking.exception.CreateAccountException;
import com.brainridge.simplebanking.model.Account;
import com.brainridge.simplebanking.repository.AccountRepository;
import com.brainridge.simplebanking.service.validators.IValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final IValidationService<CreateAccountRequestDTO> validationService;

    @Autowired
    AccountService(AccountRepository accountRepository,
                   IValidationService<CreateAccountRequestDTO> validationService) {
        this.accountRepository = accountRepository;
        this.validationService = validationService;
    }

    @Transactional
    public CreateAccountResponseDTO createAccount(CreateAccountRequestDTO request) {
        List<String> errors = validationService.validate(request);
        if (!errors.isEmpty()) {
            throw new CreateAccountException(errors);
        }
        BigDecimal balance = request.getBalance() == null ? BigDecimal.ZERO : request.getBalance();
        Account account = new Account(request.getEmail(), balance);
        accountRepository.save(account);
        return CreateAccountResponseDTO.builder()
                .id(account.getId())
                .email(account.getEmail())
                .balance(account.getBalance())
                .build();
    }

    public Account creditAmount(Long id, BigDecimal amount) {
        Account account = accountRepository.findById(id).get();
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        return account;
    }

    public Account debitAmount(Long id, BigDecimal amount) {
        Account account = accountRepository.findById(id).get();
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        return account;
    }

}
