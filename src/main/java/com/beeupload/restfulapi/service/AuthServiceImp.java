package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.auth.AuthDTO;
import com.beeupload.restfulapi.dto.user.UserDTO;
import com.beeupload.restfulapi.dto.user.UserLoginDTO;
import com.beeupload.restfulapi.dto.user.UserSignUpDTO;
import com.beeupload.restfulapi.exception.EmailExistsException;
import com.beeupload.restfulapi.exception.UserLoginNotFoundException;
import com.beeupload.restfulapi.exception.UsernameAndEmailExistsException;
import com.beeupload.restfulapi.exception.UsernameExistsException;
import com.beeupload.restfulapi.jwt.JwtService;
import com.beeupload.restfulapi.model.User;
import com.beeupload.restfulapi.repository.UserRepository;
import com.beeupload.restfulapi.security.PasswordEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncrypt passwordEncrypt;

    @Autowired
    private JwtService jwtService;

    @Override
    public UserLoginDTO login(UserLoginDTO user) throws UserLoginNotFoundException, Exception {
        boolean existUser = userRepository.findUserByUsernameAndPassword(user.getUsername(), passwordEncrypt.encrypt(user.getPassword())).isPresent();
        if (existUser){
            return new UserLoginDTO().toDTO(userRepository.findUserByUsernameAndPassword(user.getUsername(), passwordEncrypt.encrypt(user.getPassword())).get());
        }else{
            throw new UserLoginNotFoundException();
        }
    }

    @Override
    public UserSignUpDTO signUp(UserSignUpDTO user) throws UsernameAndEmailExistsException, UsernameExistsException, EmailExistsException, Exception {
        boolean existUsername = userRepository.findUserByUsername(user.getUsername()).isPresent();
        boolean existEmail = userRepository.findUserByEmail(user.getEmail()).isPresent();
        if (existUsername && existEmail){
            throw new UsernameAndEmailExistsException();
        }
        if (existUsername){
            throw new UsernameExistsException();
        }
        if (existEmail){
            throw new EmailExistsException();
        }
        user.setPassword(passwordEncrypt.encrypt(user.getPassword()));
        User model = userRepository.save(user.toModel());
        return new UserSignUpDTO().toDTO(model);
    }

    @Override
    public AuthDTO getUserLog(UserLoginDTO user) {
        return new AuthDTO().toDTO(userRepository.findUserByUsername(user.getUsername()).get(), jwtService.getToken(userRepository.findUserByUsername(user.getUsername()).get()));
    }

}
