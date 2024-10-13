package com.brainridge.simplebanking.exception;

import java.util.List;

public class CreateAccountException extends GeneralException {
    public CreateAccountException(List<String> errors) {
        super(errors);
    }
}