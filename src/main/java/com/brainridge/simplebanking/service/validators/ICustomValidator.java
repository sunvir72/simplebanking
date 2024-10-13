package com.brainridge.simplebanking.service.validators;

import java.util.List;

public interface ICustomValidator <T> {
    List<String> validate(T object);
}
