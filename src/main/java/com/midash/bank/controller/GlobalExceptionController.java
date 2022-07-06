package com.midash.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.midash.TechnicalException;
import com.midash.bank.service.EntityNotFoundException;
import com.midash.bank.service.InsufficientFundsException;


@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<ErrorResponse> handleTechnicalException(TechnicalException cause, WebRequest request) {       
        return new ResponseEntity<ErrorResponse>(
            ErrorResponse
                .builder()
                .description(cause.getMessage())
                .cause(cause.getClass().getSimpleName())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build(), 
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException cause, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(
            ErrorResponse
                .builder()
                .description(cause.getMessage())
                .errorCode(HttpStatus.NOT_FOUND.value())
                .cause(cause.getClass().getSimpleName())
                .description(cause.getEntityName())
                .build(), 
            HttpStatus.NOT_FOUND
        );        
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleBussinessException(InsufficientFundsException cause, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(
            ErrorResponse
                .builder()
                .description(cause.getMessage())
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .cause(cause.getClass().getSimpleName())
                .build(), 
            HttpStatus.BAD_REQUEST
        );        
    }

}
