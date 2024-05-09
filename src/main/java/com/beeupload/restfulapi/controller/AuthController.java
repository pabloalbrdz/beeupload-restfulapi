package com.beeupload.restfulapi.controller;


import com.beeupload.restfulapi.dto.user.UserLoginDTO;
import com.beeupload.restfulapi.dto.user.UserSignUpDTO;
import com.beeupload.restfulapi.exception.*;
import com.beeupload.restfulapi.jwt.JwtService;
import com.beeupload.restfulapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Auth Controler")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    @Operation(summary = "User Login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO account) throws Exception{
        try{
            UserLoginDTO user = authService.login(account);
            return ResponseEntity.status(HttpStatus.OK).body(authService.getUserLog(user));
        }catch (UserLoginNotFoundException unf){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(unf.getMessage());
        }
    }

    @PostMapping("/signup")
    @Operation(summary = "User Sign Up")
    public ResponseEntity<?> signUp(@RequestBody UserSignUpDTO account) throws Exception{
        try{
            UserSignUpDTO user = authService.signUp(account);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }catch (UsernameAndEmailExistsException ueee){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ueee.getMessage());
        }catch (UsernameExistsException uee){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(uee.getMessage());
        }catch (EmailExistsException eee){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(eee.getMessage());
        }
    }

    @GetMapping("/verifyusertoken/{id}/{token}")
    @Operation(summary = "Check Id & Token Id (only test)")
    public ResponseEntity<?> verifyUserToken(@RequestParam long id, @RequestParam String token){
        try{
            boolean verifyResult = jwtService.verifyUserToken(id, token);
            return ResponseEntity.status(HttpStatus.OK).body(verifyResult);
        }catch (NoAccessException nae){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(nae.getMessage());
        }
    }

}
