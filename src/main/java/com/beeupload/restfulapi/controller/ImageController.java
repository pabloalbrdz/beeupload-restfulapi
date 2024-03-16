package com.beeupload.restfulapi.controller;

import com.beeupload.restfulapi.dto.image.ImageDTO;
import com.beeupload.restfulapi.exception.ImageNotFoundException;
import com.beeupload.restfulapi.exception.UserNotFoundException;
import com.beeupload.restfulapi.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/api/image")
@Tag(name = "Image", description = "Image Controler")
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping("/saveImage")
    @Operation(summary = "Save Image")
    public ResponseEntity<?> saveImage(@RequestBody ImageDTO image){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(imageService.saveImage(image));
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }
    }

    @DeleteMapping("/deleteImage/{id}")
    @Operation(summary = "Delete Image By Id")
    public ResponseEntity<?> deleteImage(@PathVariable long id){
        try {
            imageService.deleteImage(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (ImageNotFoundException infe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(infe.getMessage());
        }
    }

}