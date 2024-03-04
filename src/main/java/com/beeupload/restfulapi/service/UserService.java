package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.UserDTO;
import com.beeupload.restfulapi.dto.UserLoginDTO;
import com.beeupload.restfulapi.dto.UserSignUpDTO;
import com.beeupload.restfulapi.exception.*;

public interface UserService {

    UserLoginDTO login(String username, String password) throws UserLoginNotFoundException, Exception;

    UserSignUpDTO signUp(UserSignUpDTO user) throws UsernameExistsException, EmailExistsException, UsernameAndEmailExistsException, Exception;

    UserDTO getUserLog(UserLoginDTO user);

    String getUserUsername(long id) throws UserNotFoundException;

    UserDTO updateUserUsername(long id, String newUsername) throws UserNotFoundException, UsernameExistsException;

    String getUserPassword(long id) throws UserNotFoundException;

    boolean checkUserPassword(long id, String password) throws UserNotFoundException, Exception;

    UserDTO updateUserPassword(long id, String newPassword) throws UserNotFoundException, Exception;

    String getUserEmail(long id) throws UserNotFoundException;

    UserDTO updateUserEmail(long id, String newEmail) throws UserNotFoundException, EmailExistsException;

    void deleteUser(long id) throws UserNotFoundException;

}
