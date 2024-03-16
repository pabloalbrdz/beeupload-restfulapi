package com.beeupload.restfulapi.exception;

public class VideoNotFoundException extends Exception{
    public VideoNotFoundException(){
        super("Error: Video no disponible.");
    }
}
