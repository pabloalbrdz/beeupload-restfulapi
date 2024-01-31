package com.beeupload.restfulapi.exception;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(){
        super("Error: Usuario o contraseña erroneos.");
    }

}
