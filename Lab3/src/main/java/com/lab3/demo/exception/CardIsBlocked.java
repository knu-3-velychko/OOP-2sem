package com.lab3.demo.exception;

public class CardIsBlocked extends RuntimeException {
    public CardIsBlocked(String message) {
        super(message);
    }
}
