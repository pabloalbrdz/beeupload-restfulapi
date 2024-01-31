package com.beeupload.restfulapi.exception;

public class EmailExistsException extends Exception{

    public EmailExistsException(){
        super("Error: Correo electronico en uso.");
    }

}
