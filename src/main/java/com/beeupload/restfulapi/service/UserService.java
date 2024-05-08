package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.user.UserDTO;
import com.beeupload.restfulapi.dto.user.UserLoginDTO;
import com.beeupload.restfulapi.dto.user.UserSignUpDTO;

import com.beeupload.restfulapi.exception.*;

public interface UserService {
    String getUserUsername(long id) throws UserNotFoundException;

    UserDTO updateUserUsername(long id, String newUsername) throws UserNotFoundException, UsernameExistsException;

    String getUserPassword(long id) throws UserNotFoundException;

    boolean checkUserPassword(long id, String password) throws UserNotFoundException, Exception;

    UserDTO updateUserPassword(long id, String newPassword) throws UserNotFoundException, Exception;

    String getUserEmail(long id) throws UserNotFoundException;

    UserDTO updateUserEmail(long id, String newEmail) throws UserNotFoundException, EmailExistsException;

    void deleteUser(long id) throws UserNotFoundException, DocumentNotFoundException, ImageNotFoundException, MusicNotFoundException, VideoNotFoundException;

}
