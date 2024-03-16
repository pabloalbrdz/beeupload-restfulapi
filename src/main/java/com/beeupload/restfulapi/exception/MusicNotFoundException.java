package com.beeupload.restfulapi.exception;

public class MusicNotFoundException extends Exception{

    public MusicNotFoundException() {
        super("Error: Cancion no disponible.");
    }
}
