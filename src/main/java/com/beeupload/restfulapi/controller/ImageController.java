package com.beeupload.restfulapi.controller;

import com.beeupload.restfulapi.exception.NoAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.beeupload.restfulapi.service.ImageService;

import com.beeupload.restfulapi.dto.image.ImageDTO;
import com.beeupload.restfulapi.dto.image.ImageDataDTO;

import com.beeupload.restfulapi.exception.ImageNotFoundException;
import com.beeupload.restfulapi.exception.UserNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@CrossOrigin
@RequestMapping("/api/image")
@Tag(name = "Image", description = "Image Controler")
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping("/saveImage")
    @Operation(summary = "Save Image")
    public ResponseEntity<?> saveImage(@RequestBody ImageDTO image, @RequestHeader("Auth") String token){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(imageService.saveImage(image, token));
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (NoAccessException nae){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @PutMapping("/updateImagePath")
    @Operation(summary = "Update Image Path")
    public ResponseEntity<?> updateImagePath(@RequestBody ImageDataDTO imageDataDTO, @RequestHeader("Auth") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(imageService.updateImagePath(imageDataDTO.getId(), imageDataDTO.getPath(), token));
        }catch (ImageNotFoundException infe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(infe.getMessage());
        }catch (NoAccessException nae){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @GetMapping("/getAllImagesById/{userid}")
    @Operation(summary = "Get All Images By User Id")
    public ResponseEntity<?> getAllUserImages(@PathVariable long userid, @RequestHeader("Auth") String token){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(imageService.getAllUserImages(userid, token));
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (NoAccessException nae){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @DeleteMapping("/deleteImage/{id}")
    @Operation(summary = "Delete Image By Id")
    public ResponseEntity<?> deleteImage(@PathVariable long id, @RequestHeader("Auth") String token){
        try {
            imageService.deleteImage(id, token);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (ImageNotFoundException infe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(infe.getMessage());
        }catch (NoAccessException nae){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @DeleteMapping("/deleteAllUserImages/{userid}")
    @Operation(summary = "Delete All Images By User Id")
    public ResponseEntity<?> deleteAllUserImages(@PathVariable long userid, @RequestHeader("Auth") String token){
        try {
            imageService.deleteAllUserImages(userid, token);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (ImageNotFoundException infe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(infe.getMessage());
        }catch (NoAccessException nae){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

}
