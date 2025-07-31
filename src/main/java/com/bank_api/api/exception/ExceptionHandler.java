package com.bank_api.api.exception;

import com.bank_api.exceptions.DuplicateEmailException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(HttpServletRequest request, BindingResult bindingResult) {

        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                request.getRequestURI(),
                "Invalid input data",
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),
                bindingResult
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorMessage);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorMessage> duplicateEmailException(HttpServletRequest request, Exception e) {

        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

}
