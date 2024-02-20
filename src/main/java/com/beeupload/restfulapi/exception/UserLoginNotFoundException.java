package com.beeupload.restfulapi.exception;

public class UserLoginNotFoundException extends Exception{

    public UserLoginNotFoundException(){
        super("Error: Usuario o contraseña erroneos.");
    }

}
