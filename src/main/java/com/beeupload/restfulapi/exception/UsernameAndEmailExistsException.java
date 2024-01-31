package com.beeupload.restfulapi.exception;

public class UsernameAndEmailExistsException extends Exception{

    public UsernameAndEmailExistsException(){
        super("Error: Nombre de usuario y correo electronico en uso.");
    }

}
