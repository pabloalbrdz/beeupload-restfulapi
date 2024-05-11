package com.beeupload.restfulapi.exception;

public class NoAccessException extends Exception{

    public NoAccessException() {
        super("Error: No tienes permiso para esta accion.");
    }
}
