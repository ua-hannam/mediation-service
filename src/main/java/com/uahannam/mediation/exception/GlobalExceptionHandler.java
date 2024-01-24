package com.uahannam.mediation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.uahannam.mediation")
public class GlobalExceptionHandler {

    @ExceptionHandler(MediationException.class)
    public ResponseEntity<ErrorCode> handleMenuException(MediationException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleOtherExceptions(Exception ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

}
