package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.UserLoginDTO;
import com.beeupload.restfulapi.dto.UserSignUpDTO;
import com.beeupload.restfulapi.exception.EmailExistsException;
import com.beeupload.restfulapi.exception.UsernameAndEmailExistsException;
import com.beeupload.restfulapi.exception.UsernameExistsException;
import com.beeupload.restfulapi.model.User;
import com.beeupload.restfulapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserLoginDTO login(String username, String password) {
        User model = userRepository.findUserByUsernameAndPassword(username, password);
        if (model != null){
            return new UserLoginDTO().toDTO(model);
        }else{
            return null;
        }
    }

    @Override
    public UserSignUpDTO signUp(UserSignUpDTO user) throws UsernameAndEmailExistsException, UsernameExistsException, EmailExistsException {
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
        User model = userRepository.save(user.toModel());
        return new UserSignUpDTO().toDTO(model);
    }

}
