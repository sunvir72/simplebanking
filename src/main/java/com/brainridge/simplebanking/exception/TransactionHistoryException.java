package com.brainridge.simplebanking.exception;

import java.util.List;

public class TransactionHistoryException extends GeneralException {
    public TransactionHistoryException(List<String> errors) {
        super(errors);
    }
}
