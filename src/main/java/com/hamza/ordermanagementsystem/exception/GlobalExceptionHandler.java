package com.hamza.ordermanagementsystem.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {

        return ResponseEntity.status(404)
                .body(ErrorResponse.builder()
                        .message(ex.getMessage())
                        .status(404)
                        .build());
    }
}