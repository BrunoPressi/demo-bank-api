package com.bank_api.web.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorMessage {

    @JsonFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private LocalDateTime timestamp;
    private String path;
    private String message;
    private int code;
    private String statusMessage;

    private HashMap<String, String> errors = new HashMap<>();

    public ErrorMessage(LocalDateTime timestamp, String path, String message, int code, String statusMessage) {
        this.timestamp = timestamp;
        this.path = path;
        this.message = message;
        this.code = code;
        this.statusMessage = statusMessage;
    }

    public ErrorMessage(LocalDateTime timestamp, String path, String message, int code, String statusMessage, BindingResult bindingResult) {
        this.timestamp = timestamp;
        this.path = path;
        this.message = message;
        this.code = code;
        this.statusMessage = statusMessage;
        addErros(bindingResult);
    }

    private void addErros(BindingResult bindingResult) {
        List<FieldError> errorsList = bindingResult.getFieldErrors();
        for (FieldError erro : errorsList) {
            errors.put(erro.getField(), erro.getDefaultMessage());
        }
    }
}
