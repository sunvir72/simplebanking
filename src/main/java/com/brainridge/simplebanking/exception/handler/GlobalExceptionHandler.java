package com.brainridge.simplebanking.exception.handler;

import com.brainridge.simplebanking.dto.ErrorResponse;
import com.brainridge.simplebanking.exception.CreateAccountException;
import com.brainridge.simplebanking.exception.TransactionHistoryException;
import com.brainridge.simplebanking.exception.TransferFundsException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({CreateAccountException.class, TransferFundsException.class, TransactionHistoryException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleCustomExceptions(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessages = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + Optional.ofNullable(error.getDefaultMessage()).orElse("Invalid field"))
                .collect(Collectors.joining(" | "));
        ErrorResponse errorResponse = new ErrorResponse(errorMessages, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, InvalidFormatException.class})
    public ResponseEntity<ErrorResponse> handleParsingExceptions(HttpMessageNotReadableException ex) {
        String message = ex.getCause().getMessage();
        return new ResponseEntity<>(
                new ErrorResponse(message, HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
