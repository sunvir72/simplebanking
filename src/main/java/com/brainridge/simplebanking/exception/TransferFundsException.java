package com.brainridge.simplebanking.exception;

import java.util.List;

public class TransferFundsException extends GeneralException {
    public TransferFundsException(List<String> errors) {
        super(errors);
    }
}
