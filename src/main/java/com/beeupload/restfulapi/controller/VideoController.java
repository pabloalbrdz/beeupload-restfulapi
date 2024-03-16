package com.beeupload.restfulapi.controller;

import com.beeupload.restfulapi.dto.video.VideoDTO;
import com.beeupload.restfulapi.exception.VideoNotFoundException;
import com.beeupload.restfulapi.exception.UserNotFoundException;
import com.beeupload.restfulapi.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/api/video")
@Tag(name = "Video", description = "Video Controler")
public class VideoController {

    @Autowired
    VideoService videoService;

    @PostMapping("/saveVideo")
    @Operation(summary = "Save Video")
    public ResponseEntity<?> saveVideo(@RequestBody VideoDTO video){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(videoService.saveVideo(video));
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }
    }

    @GetMapping("/getAllVideosById/{userid}")
    @Operation(summary = "Get All Videos By User Id")
    public ResponseEntity<?> getAllUserVideos(@PathVariable long userid){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(videoService.getAllUserVideos(userid));
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }
    }

    @DeleteMapping("/deleteVideo/{id}")
    @Operation(summary = "Delete Video By Id")
    public ResponseEntity<?> deleteVideo(@PathVariable long id){
        try {
            videoService.deleteVideo(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (VideoNotFoundException vnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(vnfe.getMessage());
        }
    }

    @DeleteMapping("/deleteAllUserVideos/{userid}")
    @Operation(summary = "Delete All Videos By User Id")
    public ResponseEntity<?> deleteAllUserVideos(@PathVariable long userid){
        try {
            videoService.deleteAllUserVideos(userid);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (VideoNotFoundException vnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(vnfe.getMessage());
        }
    }

}
