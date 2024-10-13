package com.brainridge.simplebanking.service.validators;

import com.brainridge.simplebanking.dto.CreateAccountRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateAccountValidationService implements IValidationService<CreateAccountRequestDTO> {

    private final List<ICustomValidator<CreateAccountRequestDTO>> validators;

    @Autowired
    public CreateAccountValidationService(List<ICustomValidator<CreateAccountRequestDTO>> validators) {
        this.validators = validators;
    }

    @Override
    public List<String> validate(CreateAccountRequestDTO requestDTO) {
        List<String> errors = new ArrayList<>();
        for (ICustomValidator<CreateAccountRequestDTO> validator : validators) {
            errors.addAll(validator.validate(requestDTO));
        }
        return errors;
    }
}
