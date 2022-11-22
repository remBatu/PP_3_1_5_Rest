package ru.kata.spring.boot_security.demo.Exceptions;

public class UserNotValidException extends RuntimeException{
    public UserNotValidException(String message) {
        super(message);
    }
}
