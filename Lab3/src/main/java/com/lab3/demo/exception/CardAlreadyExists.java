package com.lab3.demo.exception;

public class CardAlreadyExists extends RuntimeException {
    public CardAlreadyExists(String message) {
        super(message);
    }
}
