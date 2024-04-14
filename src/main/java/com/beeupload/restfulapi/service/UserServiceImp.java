package com.beeupload.restfulapi.service;

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
    public UserDTO getUserLog(UserLoginDTO user) {
        return new UserDTO().toDTO(userRepository.findUserByUsername(user.getUsername()).get());
    }

    @Override
    public String getUserUsername(long id) throws UserNotFoundException {
        String username = userRepository.findUsernameById(id);
        if (username == null){
            throw new UserNotFoundException();
        }
        return username;
    }

    @Override
    public UserDTO updateUserUsername(long id, String newUsername) throws UserNotFoundException, UsernameExistsException {
        boolean userIdAvaiable = userRepository.findById(id).isPresent();
        if (!userIdAvaiable){
            throw new UserNotFoundException();
        }
        boolean userWithUsernameAvaiable = userRepository.findUserByUsername(newUsername).isPresent();
        if (userWithUsernameAvaiable){
            throw new UsernameExistsException();
        }
        User user = userRepository.findById(id).get();
        user.setUsername(newUsername);
        userRepository.save(user);
        return new UserDTO().toDTO(user);
    }

    @Override
    public String getUserPassword(long id) throws UserNotFoundException {
        String password = userRepository.findPasswordById(id);
        if (password == null){
            throw new UserNotFoundException();
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
    public UserDTO updateUserPassword(long id, String newPassword) throws UserNotFoundException, Exception {
        boolean userIdAvaiable = userRepository.findById(id).isPresent();
        if (!userIdAvaiable){
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(id).get();
        user.setPassword(passwordEncrypt.encrypt(newPassword));
        userRepository.save(user);
        return new UserDTO().toDTO(user);
    }

    @Override
    public String getUserEmail(long id) throws UserNotFoundException {
        String email = userRepository.findEmailById(id);
        if (email == null){
            throw new UserNotFoundException();
        }
        return email;
    }

    @Override
    public UserDTO updateUserEmail(long id, String newEmail) throws UserNotFoundException, EmailExistsException {
        boolean userIdAvaiable = userRepository.findById(id).isPresent();
        if (!userIdAvaiable){
            throw new UserNotFoundException();
        }
        boolean userWithEmailAvaiable = userRepository.findUserByEmail(newEmail).isPresent();
        if (userWithEmailAvaiable){
            throw new EmailExistsException();
        }
        User user = userRepository.findById(id).get();
        user.setEmail(newEmail);
        userRepository.save(user);
        return new UserDTO().toDTO(user);
    }

    @Override
    public void deleteUser(long id) throws UserNotFoundException, DocumentNotFoundException, ImageNotFoundException, MusicNotFoundException, VideoNotFoundException {
        boolean userIdAvaiable = userRepository.findById(id).isPresent();
        if (!userIdAvaiable){
            throw new UserNotFoundException();
        }
        documentService.deleteAllUserDocuments(id);
        imageService.deleteAllUserImages(id);
        musicService.deleteAllUserMusic(id);
        videoService.deleteAllUserVideos(id);
        userRepository.deleteById(id);
    }

}
