package com.runnermann.wolf.example.ext.exception;

public class MethodNotImplementedException extends Exception {

    public MethodNotImplementedException() {
        super("Method not implemented");
    }

    public MethodNotImplementedException(String message) {
        super(message);
    }
}
