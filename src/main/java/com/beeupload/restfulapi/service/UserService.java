package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.UserDTO;
import com.beeupload.restfulapi.dto.UserLoginDTO;
import com.beeupload.restfulapi.dto.UserSignUpDTO;
import com.beeupload.restfulapi.exception.EmailExistsException;
import com.beeupload.restfulapi.exception.UserNotFoundException;
import com.beeupload.restfulapi.exception.UsernameAndEmailExistsException;
import com.beeupload.restfulapi.exception.UsernameExistsException;

public interface UserService {

    UserLoginDTO login(String username, String password) throws UserNotFoundException, Exception;

    UserSignUpDTO signUp(UserSignUpDTO user) throws UsernameExistsException, EmailExistsException, UsernameAndEmailExistsException, Exception;

    UserDTO getUserLog(UserLoginDTO user);

}
