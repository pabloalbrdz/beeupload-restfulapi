package com.beeupload.restfulapi.controller;

import com.beeupload.restfulapi.exception.NoAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.beeupload.restfulapi.service.VideoService;

import com.beeupload.restfulapi.dto.video.VideoDTO;
import com.beeupload.restfulapi.dto.video.VideoDataDTO;

import com.beeupload.restfulapi.exception.VideoNotFoundException;
import com.beeupload.restfulapi.exception.UserNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@CrossOrigin
@RequestMapping("/api/video")
@Tag(name = "Video", description = "Video Controler")
public class VideoController {

    @Autowired
    VideoService videoService;

    @PostMapping("/saveVideo")
    @Operation(summary = "Save Video")
    public ResponseEntity<?> saveVideo(@RequestBody VideoDTO video, @RequestHeader("Auth") String token){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(videoService.saveVideo(video, token));
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (NoAccessException nae){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @PutMapping("/updateVideo")
    @Operation(summary = "Update Video Path")
    public ResponseEntity<?> updateVideo(@RequestBody VideoDataDTO videoDataDTO, @RequestHeader("Auth") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(videoService.updateVideo(videoDataDTO.getId(), videoDataDTO, token));
        }catch (VideoNotFoundException vnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(vnfe.getMessage());
        }catch (NoAccessException nae){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @PutMapping("/updateVideoPath")
    @Operation(summary = "Update Video Path")
    public ResponseEntity<?> updateVideoPath(@RequestBody VideoDataDTO videoDataDTO, @RequestHeader("Auth") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(videoService.updateVideoPath(videoDataDTO.getId(), videoDataDTO.getPath(), token));
        }catch (VideoNotFoundException vnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(vnfe.getMessage());
        }catch (NoAccessException nae){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @GetMapping("/getAllVideosById/{userid}")
    @Operation(summary = "Get All Videos By User Id")
    public ResponseEntity<?> getAllUserVideos(@PathVariable long userid, @RequestHeader("Auth") String token){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(videoService.getAllUserVideos(userid, token));
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (NoAccessException nae){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @DeleteMapping("/deleteVideo/{id}")
    @Operation(summary = "Delete Video By Id")
    public ResponseEntity<?> deleteVideo(@PathVariable long id, @RequestHeader("Auth") String token){
        try {
            videoService.deleteVideo(id, token);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (VideoNotFoundException vnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(vnfe.getMessage());
        }catch (NoAccessException nae){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @DeleteMapping("/deleteAllUserVideos/{userid}")
    @Operation(summary = "Delete All Videos By User Id")
    public ResponseEntity<?> deleteAllUserVideos(@PathVariable long userid, @RequestHeader("Auth") String token){
        try {
            videoService.deleteAllUserVideos(userid, token);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (VideoNotFoundException vnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(vnfe.getMessage());
        }catch (NoAccessException nae){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

}
