package com.beeupload.restfulapi.exception;

public class ImageNotFoundException extends Exception{

    public ImageNotFoundException() {
        super("Error: Imagen no disponible.");
    }
}
