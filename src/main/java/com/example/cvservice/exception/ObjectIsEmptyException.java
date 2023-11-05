package com.example.cvservice.exception;

public class ObjectIsEmptyException extends RuntimeException {

    public ObjectIsEmptyException() {
        super("Incoming object is empty!");
    }

}
