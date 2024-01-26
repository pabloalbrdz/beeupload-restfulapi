package com.beeupload.restfulapi.controller;

import com.beeupload.restfulapi.dto.UserLoginDTO;
import com.beeupload.restfulapi.dto.UserSignUpDTO;
import com.beeupload.restfulapi.exception.EmailExistsException;
import com.beeupload.restfulapi.exception.UsernameAndEmailExistsException;
import com.beeupload.restfulapi.exception.UsernameExistsException;
import com.beeupload.restfulapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login/{username}/{password}")
    @Operation(summary = "Login")
    public ResponseEntity<?> login(@PathVariable String username, @PathVariable String password) throws Exception{
        UserLoginDTO user = userService.login(username, password);
        if (user != null){
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserLog(user));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/signup")
    @Operation(summary = "Sign Up")
    public ResponseEntity<?> signUp(@RequestBody UserSignUpDTO account) throws Exception{
        try{
            UserSignUpDTO user = userService.signUp(account);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }catch (UsernameAndEmailExistsException ueee){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ueee.getMessage());
        }catch (UsernameExistsException uee){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(uee.getMessage());
        }catch (EmailExistsException eee){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(eee.getMessage());
        }
    }

}
