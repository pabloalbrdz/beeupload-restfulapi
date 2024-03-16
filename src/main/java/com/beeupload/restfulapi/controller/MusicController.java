package com.beeupload.restfulapi.controller;

import com.beeupload.restfulapi.dto.music.MusicDTO;
import com.beeupload.restfulapi.exception.MusicNotFoundException;
import com.beeupload.restfulapi.exception.UserNotFoundException;
import com.beeupload.restfulapi.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/api/music")
@Tag(name = "Music", description = "Music Controller")
public class MusicController {

    @Autowired
    MusicService musicService;

    @PostMapping("/saveMusic")
    @Operation(summary = "Save Music")
    public ResponseEntity<?> saveMusic(@RequestBody MusicDTO music){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(musicService.saveMusic(music));
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }
    }

    @GetMapping("/getAllMusicById/{userid}")
    @Operation(summary = "Get All Music By User Id")
    public ResponseEntity<?> getAllUserMusic(@PathVariable long userid){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(musicService.getAllUserMusic(userid));
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }
    }

    @DeleteMapping("/deleteMusic/{id}")
    @Operation(summary = "Delete Music By Id")
    public ResponseEntity<?> deleteMusic(@PathVariable long id){
        try {
            musicService.deleteMusic(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (MusicNotFoundException mnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mnfe.getMessage());
        }
    }

    @DeleteMapping("/deleteAllUserMusic/{userid}")
    @Operation(summary = "Delete All Music By User Id")
    public ResponseEntity<?> deleteAllUserMusic(@PathVariable long userid){
        try {
            musicService.deleteAllUserMusic(userid);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (MusicNotFoundException mnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mnfe.getMessage());
        }
    }

}
