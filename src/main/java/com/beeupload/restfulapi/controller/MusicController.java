package com.beeupload.restfulapi.controller;

import com.beeupload.restfulapi.exception.NoAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.beeupload.restfulapi.service.MusicService;

import com.beeupload.restfulapi.dto.music.MusicDTO;
import com.beeupload.restfulapi.dto.music.MusicDataDTO;

import com.beeupload.restfulapi.exception.MusicNotFoundException;
import com.beeupload.restfulapi.exception.UserNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@CrossOrigin
@RequestMapping("/api/music")
@Tag(name = "Music", description = "Music Controller")
public class MusicController {

    @Autowired
    MusicService musicService;

    @PostMapping("/saveMusic")
    @Operation(summary = "Save Music")
    public ResponseEntity<?> saveMusic(@RequestBody MusicDTO music, @RequestHeader("Auth") String token){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(musicService.saveMusic(music, token));
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (NoAccessException nae){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @PutMapping("/updateMusic")
    @Operation(summary = "Update Music Path")
    public ResponseEntity<?> updateMusic(@RequestBody MusicDataDTO musicDataDTO, @RequestHeader("Auth") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(musicService.updateMusic(musicDataDTO.getId(), musicDataDTO, token));
        }catch (MusicNotFoundException mnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mnfe.getMessage());
        }catch (NoAccessException nae){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @PutMapping("/updateMusicPath")
    @Operation(summary = "Update Music Path")
    public ResponseEntity<?> updateMusicPath(@RequestBody MusicDataDTO musicDataDTO, @RequestHeader("Auth") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(musicService.updateMusicPath(musicDataDTO.getId(), musicDataDTO.getPath(), token));
        }catch (MusicNotFoundException mnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mnfe.getMessage());
        }catch (NoAccessException nae){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @GetMapping("/getAllMusicById/{userid}")
    @Operation(summary = "Get All Music By User Id")
    public ResponseEntity<?> getAllUserMusic(@PathVariable long userid, @RequestHeader("Auth") String token){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(musicService.getAllUserMusic(userid, token));
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (NoAccessException nae){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @DeleteMapping("/deleteMusic/{id}")
    @Operation(summary = "Delete Music By Id")
    public ResponseEntity<?> deleteMusic(@PathVariable long id, @RequestHeader("Auth") String token){
        try {
            musicService.deleteMusic(id, token);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (MusicNotFoundException mnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mnfe.getMessage());
        }catch (NoAccessException nae){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @DeleteMapping("/deleteAllUserMusic/{userid}")
    @Operation(summary = "Delete All Music By User Id")
    public ResponseEntity<?> deleteAllUserMusic(@PathVariable long userid, @RequestHeader("Auth") String token){
        try {
            musicService.deleteAllUserMusic(userid, token);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (MusicNotFoundException mnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mnfe.getMessage());
        }catch (NoAccessException nae){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

}
