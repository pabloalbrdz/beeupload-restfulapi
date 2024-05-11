package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.auth.AuthDTO;
import com.beeupload.restfulapi.dto.user.UserLoginDTO;
import com.beeupload.restfulapi.dto.user.UserSignUpDTO;
import com.beeupload.restfulapi.exception.EmailExistsException;
import com.beeupload.restfulapi.exception.UserLoginNotFoundException;
import com.beeupload.restfulapi.exception.UsernameAndEmailExistsException;
import com.beeupload.restfulapi.exception.UsernameExistsException;

public interface AuthService {

    UserLoginDTO login(UserLoginDTO user) throws UserLoginNotFoundException, Exception;

    UserSignUpDTO signUp(UserSignUpDTO user) throws UsernameExistsException, EmailExistsException, UsernameAndEmailExistsException, Exception;

    AuthDTO getUserLog(UserLoginDTO user);

}
