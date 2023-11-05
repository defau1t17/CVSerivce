package com.example.cvservice.exception;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ObjectAlreadyExistsException.class)
    public ResponseEntity<Object> handleObjectAlreadyExistsException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Object ";
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = ObjectIsEmptyException.class)
    public ResponseEntity<Object> handleObjectIsEmptyException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = ObjectNotFoundException.class)
    public ResponseEntity<Object> handleObjectNotFoundException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


}
