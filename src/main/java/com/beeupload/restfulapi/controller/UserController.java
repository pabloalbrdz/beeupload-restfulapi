package com.beeupload.restfulapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.beeupload.restfulapi.service.UserService;

import com.beeupload.restfulapi.dto.user.UserDTO;
import com.beeupload.restfulapi.dto.user.UserLoginDTO;
import com.beeupload.restfulapi.dto.user.UserSignUpDTO;

import com.beeupload.restfulapi.exception.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@CrossOrigin
@RequestMapping("/api/user")
@Tag(name = "User", description = "User Controler")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserUsername/{id}")
    @Operation(summary = "Get User Username By Id")
    public ResponseEntity<String> getUserUsername(@PathVariable long id) throws Exception{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserUsername(id));
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }
    }

    @PatchMapping("/updateUserUsername/{id}/{newUsername}")
    @Operation(summary = "Update User Username By Id")
    public ResponseEntity<?> updateUserUsername(@PathVariable long id, @PathVariable String newUsername, @RequestHeader("Auth") String token) throws Exception{
        try{
            UserDTO user = userService.updateUserUsername(id, newUsername, token);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (UsernameExistsException uee){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(uee.getMessage());
        }catch (NoAccessException nae){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @GetMapping("/getUserPassword/{id}")
    @Operation(summary = "Get User Password By Id")
    public ResponseEntity<String> getUserPassword(@PathVariable long id, @RequestHeader("Auth") String token) throws Exception{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserPassword(id, token));
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (NoAccessException nae){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @GetMapping("/checkUserPassword/{id}/{password}")
    @Operation(summary = "Check User Password")
    public ResponseEntity<?> checkUserPassword(@PathVariable long id, @PathVariable String password) throws Exception{
        try{
           boolean result = userService.checkUserPassword(id, password);
           return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }
    }

    @PatchMapping("/updateUserPassword/{id}/{newPassword}")
    @Operation(summary = "Update User Password By Id")
    public ResponseEntity<?> updateUserPassword(@PathVariable long id, @PathVariable String newPassword, @RequestHeader("Auth") String token) throws Exception{
        try{
            UserDTO user = userService.updateUserPassword(id, newPassword, token);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (NoAccessException nae){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @GetMapping("/getUserEmail/{id}")
    @Operation(summary = "Get User Email By Id")
    public ResponseEntity<String> getUserEmail(@PathVariable long id, @RequestHeader("Auth") String token) throws Exception{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserEmail(id, token));
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (NoAccessException nae){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @PatchMapping("/updateUserEmail/{id}/{newEmail}")
    @Operation(summary = "Update User Email By Id")
    public ResponseEntity<?> updateUserEmail(@PathVariable long id, @PathVariable String newEmail, @RequestHeader("Auth") String token) throws Exception{
        try{
            UserDTO user = userService.updateUserEmail(id, newEmail, token);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (EmailExistsException eee){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(eee.getMessage());
        }catch (NoAccessException nae){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    @Operation(summary = "Delete User By Id")
    public ResponseEntity<?> deleteUser(@PathVariable long id, @RequestHeader("Auth") String token) throws Exception{
        try{
            userService.deleteUser(id, token);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (DocumentNotFoundException dnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dnfe.getMessage());
        }catch (ImageNotFoundException infe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(infe.getMessage());
        }catch (MusicNotFoundException mnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mnfe.getMessage());
        }catch (VideoNotFoundException vnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(vnfe.getMessage());
        }catch (NoAccessException nae){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

}
