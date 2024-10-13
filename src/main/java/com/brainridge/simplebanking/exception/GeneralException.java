package com.brainridge.simplebanking.exception;

import java.util.List;

public class GeneralException extends RuntimeException {
    public GeneralException(List<String> errors) {
        super(String.join("| ", errors));
    }
}
