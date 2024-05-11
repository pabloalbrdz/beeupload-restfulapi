package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.user.UserDTO;
import com.beeupload.restfulapi.dto.user.UserLoginDTO;
import com.beeupload.restfulapi.dto.user.UserSignUpDTO;

import com.beeupload.restfulapi.exception.*;

public interface UserService {
    String getUserUsername(long id) throws UserNotFoundException;

    UserDTO updateUserUsername(long id, String newUsername, String token) throws UserNotFoundException, UsernameExistsException, NoAccessException;

    String getUserPassword(long id, String token) throws UserNotFoundException, NoAccessException;

    boolean checkUserPassword(long id, String password) throws UserNotFoundException, Exception;

    UserDTO updateUserPassword(long id, String newPassword, String token) throws UserNotFoundException, Exception, NoAccessException;

    String getUserEmail(long id, String token) throws UserNotFoundException, NoAccessException;

    UserDTO updateUserEmail(long id, String newEmail, String token) throws UserNotFoundException, EmailExistsException, NoAccessException;

    void deleteUser(long id, String token) throws UserNotFoundException, DocumentNotFoundException, ImageNotFoundException, MusicNotFoundException, VideoNotFoundException, NoAccessException;

}
