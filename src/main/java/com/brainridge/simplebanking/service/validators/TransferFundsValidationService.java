package com.brainridge.simplebanking.service.validators;

import com.brainridge.simplebanking.dto.TransferFundsRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransferFundsValidationService implements IValidationService<TransferFundsRequestDTO> {

    private final List<ICustomValidator<TransferFundsRequestDTO>> validators;

    @Autowired
    public TransferFundsValidationService(List<ICustomValidator<TransferFundsRequestDTO>> validators) {
        this.validators = validators;
    }

    @Override
    public List<String> validate(TransferFundsRequestDTO requestDTO) {
        List<String> errors = new ArrayList<>();
        for (ICustomValidator<TransferFundsRequestDTO> validator : validators) {
            errors.addAll(validator.validate(requestDTO));
        }
        return errors;
    }
}
