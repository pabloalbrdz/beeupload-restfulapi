package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.UserDTO;
import com.beeupload.restfulapi.dto.UserLoginDTO;
import com.beeupload.restfulapi.dto.UserSignUpDTO;
import com.beeupload.restfulapi.exception.EmailExistsException;
import com.beeupload.restfulapi.exception.UsernameAndEmailExistsException;
import com.beeupload.restfulapi.exception.UsernameExistsException;
import com.beeupload.restfulapi.model.User;
import com.beeupload.restfulapi.repository.UserRepository;
import com.beeupload.restfulapi.security.PasswordEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncrypt passwordEncrypt;

    @Override
    public UserLoginDTO login(String username, String password) throws Exception {
        User model = userRepository.findUserByUsernameAndPassword(username, passwordEncrypt.encrypt(password));
        if (model != null){
            return new UserLoginDTO().toDTO(model);
        }else{
            return null;
        }
    }

    @Override
    public UserSignUpDTO signUp(UserSignUpDTO user) throws UsernameAndEmailExistsException, UsernameExistsException, EmailExistsException, Exception {
        User existUsername = userRepository.findUserByUsername(user.getUsername());
        User existEmail = userRepository.findUserByEmail(user.getEmail());
        if (existUsername != null && existEmail != null){
            throw new UsernameAndEmailExistsException();
        }
        if (existUsername != null){
            throw new UsernameExistsException();
        }
        if (existEmail != null){
            throw new EmailExistsException();
        }
        user.setPassword(passwordEncrypt.encrypt(user.getPassword()));
        User model = userRepository.save(user.toModel());
        return new UserSignUpDTO().toDTO(model);
    }

    @Override
    public UserDTO getUserLog(UserLoginDTO user) {
        return new UserDTO().toDTO(userRepository.findUserByUsername(user.getUsername()));
    }

}
