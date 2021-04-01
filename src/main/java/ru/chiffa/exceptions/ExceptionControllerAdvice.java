package ru.chiffa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(
                new MarketError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleConflictException(ConflictException e) {
        return new ResponseEntity<>(
                new MarketError(HttpStatus.CONFLICT.value(), e.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler
    public ResponseEntity<?> handleForbiddenException(ForbiddenException e) {
        return new ResponseEntity<>(
                new MarketError(HttpStatus.FORBIDDEN.value(), e.getMessage()),
                HttpStatus.FORBIDDEN
        );
    }
}
