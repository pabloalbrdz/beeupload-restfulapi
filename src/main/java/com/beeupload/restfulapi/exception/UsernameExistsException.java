package com.beeupload.restfulapi.exception;

public class UsernameExistsException extends Exception{

    public UsernameExistsException(){
        super("Error: Nombre de usuario en uso.");
    }

}
