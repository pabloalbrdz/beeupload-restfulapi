package com.beeupload.restfulapi.exception;

public class DocumentNotFoundException extends Exception{
    public DocumentNotFoundException(){
        super("Error: Documento no disponible.");
    }
}
