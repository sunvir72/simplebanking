package com.brainridge.simplebanking.service.validators;

import com.brainridge.simplebanking.dto.CreateAccountRequestDTO;
import com.brainridge.simplebanking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateAccountValidator implements ICustomValidator<CreateAccountRequestDTO> {

    private final AccountRepository accountRepository;

    @Autowired
    public CreateAccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<String> validate(CreateAccountRequestDTO request) {
        List<String> errors = new ArrayList<>();
        if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            errors.add("Account already exists");//TODO: Create Enum for errors containing error message and code
        }
        return errors;
    }
}
