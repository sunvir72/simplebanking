package com.brainridge.simplebanking.service.validators;

import com.brainridge.simplebanking.dto.TransactionHistoryRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionHistoryValidationService implements IValidationService<TransactionHistoryRequestDTO> {
    private final List<ICustomValidator<TransactionHistoryRequestDTO>> validators;

    @Autowired
    public TransactionHistoryValidationService(List<ICustomValidator<TransactionHistoryRequestDTO>> validators) {
        this.validators = validators;
    }


    @Override
    public List<String> validate(TransactionHistoryRequestDTO requestDTO) {
        List<String> errors = new ArrayList<>();
        for (ICustomValidator<TransactionHistoryRequestDTO> validator : validators) {
            errors.addAll(validator.validate(requestDTO));
        }
        return errors;
    }
}
