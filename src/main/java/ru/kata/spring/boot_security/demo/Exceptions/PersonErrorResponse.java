package ru.kata.spring.boot_security.demo.Exceptions;

public class PersonErrorResponse {
    private String message;

    private PersonErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static PersonErrorResponse getErrorResponse (String message) {
        return new PersonErrorResponse (message);
    }
}
