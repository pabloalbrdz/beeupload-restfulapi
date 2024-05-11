package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beeupload.restfulapi.model.User;

import com.beeupload.restfulapi.dto.user.UserDTO;
import com.beeupload.restfulapi.dto.user.UserLoginDTO;
import com.beeupload.restfulapi.dto.user.UserSignUpDTO;

import com.beeupload.restfulapi.exception.*;

import com.beeupload.restfulapi.repository.UserRepository;

import com.beeupload.restfulapi.security.PasswordEncrypt;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncrypt passwordEncrypt;

    @Autowired
    DocumentService documentService;

    @Autowired
    ImageService imageService;

    @Autowired
    MusicService musicService;

    @Autowired
    VideoService videoService;

    @Autowired
    JwtService jwtService;

    @Override
    public String getUserUsername(long id) throws UserNotFoundException {
        String username = userRepository.findUsernameById(id);
        if (username == null){
            throw new UserNotFoundException();
        }
        return username;
    }

    @Override
    public UserDTO updateUserUsername(long id, String newUsername, String token) throws UserNotFoundException, UsernameExistsException, NoAccessException {
        boolean userIdAvaiable = userRepository.findById(id).isPresent();
        if (!userIdAvaiable){
            throw new UserNotFoundException();
        }
        boolean userWithUsernameAvaiable = userRepository.findUserByUsername(newUsername).isPresent();
        if (userWithUsernameAvaiable){
            throw new UsernameExistsException();
        }
        User user = userRepository.findById(id).get();
        boolean authorized = jwtService.verifyUserToken(id, token);
        if (!authorized){
            throw new NoAccessException();
        }
        user.setUsername(newUsername);
        userRepository.save(user);
        return new UserDTO().toDTO(user);
    }

    @Override
    public String getUserPassword(long id, String token) throws UserNotFoundException, NoAccessException {
        String password = userRepository.findPasswordById(id);
        if (password == null){
            throw new UserNotFoundException();
        }
        boolean authorized = jwtService.verifyUserToken(id, token);
        if (!authorized){
            throw new NoAccessException();
        }
        return password;
    }

    @Override
    public boolean checkUserPassword(long id, String password) throws UserNotFoundException, Exception {
        boolean userIdAvaiable = userRepository.findById(id).isPresent();
        if (!userIdAvaiable){
            throw new UserNotFoundException();
        }
        if (passwordEncrypt.encrypt(password).equals(userRepository.findPasswordById(id))){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public UserDTO updateUserPassword(long id, String newPassword, String token) throws UserNotFoundException, Exception, NoAccessException {
        boolean userIdAvaiable = userRepository.findById(id).isPresent();
        if (!userIdAvaiable){
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(id).get();
        boolean authorized = jwtService.verifyUserToken(id, token);
        if (!authorized){
            throw new NoAccessException();
        }
        user.setPassword(passwordEncrypt.encrypt(newPassword));
        userRepository.save(user);
        return new UserDTO().toDTO(user);
    }

    @Override
    public String getUserEmail(long id, String token) throws UserNotFoundException, NoAccessException {
        String email = userRepository.findEmailById(id);
        if (email == null){
            throw new UserNotFoundException();
        }
        boolean authorized = jwtService.verifyUserToken(id, token);
        if (!authorized){
            throw new NoAccessException();
        }
        return email;
    }

    @Override
    public UserDTO updateUserEmail(long id, String newEmail, String token) throws UserNotFoundException, EmailExistsException, NoAccessException {
        boolean userIdAvaiable = userRepository.findById(id).isPresent();
        if (!userIdAvaiable){
            throw new UserNotFoundException();
        }
        boolean userWithEmailAvaiable = userRepository.findUserByEmail(newEmail).isPresent();
        if (userWithEmailAvaiable){
            throw new EmailExistsException();
        }
        User user = userRepository.findById(id).get();
        boolean authorized = jwtService.verifyUserToken(id, token);
        if (!authorized){
            throw new NoAccessException();
        }
        user.setEmail(newEmail);
        userRepository.save(user);
        return new UserDTO().toDTO(user);
    }

    @Override
    public void deleteUser(long id, String token) throws UserNotFoundException, DocumentNotFoundException, ImageNotFoundException, MusicNotFoundException, VideoNotFoundException, NoAccessException {
        boolean userIdAvaiable = userRepository.findById(id).isPresent();
        if (!userIdAvaiable){
            throw new UserNotFoundException();
        }
        documentService.deleteAllUserDocuments(id, token);
        imageService.deleteAllUserImages(id, token);
        musicService.deleteAllUserMusic(id, token);
        videoService.deleteAllUserVideos(id, token);
        boolean authorized = jwtService.verifyUserToken(id, token);
        if (!authorized){
            throw new NoAccessException();
        }
        userRepository.deleteById(id);
    }

}
