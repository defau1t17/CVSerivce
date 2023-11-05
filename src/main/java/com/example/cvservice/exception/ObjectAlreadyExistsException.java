package com.example.cvservice.exception;

public class ObjectAlreadyExistsException extends RuntimeException{

    public ObjectAlreadyExistsException(String objName,String name){
        super(objName + " with name " + name + " already exists");
    }
}
