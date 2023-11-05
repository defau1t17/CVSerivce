package com.example.cvservice.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String objName, Long id) {
        super(objName + "with ID " + id + " not found");
    }
}
