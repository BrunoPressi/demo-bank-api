package com.bank_api.exceptions;


public class UuidNotValidException extends RuntimeException {
    public UuidNotValidException(String msg) {
        super(msg);
    }
}
