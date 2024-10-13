package com.brainridge.simplebanking.service.validators;

import java.util.List;

public interface IValidationService<T> {

    List<String> validate(T requestDTO);
}
