package com.beeupload.restfulapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beeupload.restfulapi.model.Image;
import com.beeupload.restfulapi.model.User;

import com.beeupload.restfulapi.dto.image.ImageDTO;
import com.beeupload.restfulapi.dto.image.ImageDataDTO;

import com.beeupload.restfulapi.exception.ImageNotFoundException;
import com.beeupload.restfulapi.exception.UserNotFoundException;

import com.beeupload.restfulapi.repository.ImageRepository;
import com.beeupload.restfulapi.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ImageServiceImp implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ImageDataDTO saveImage(ImageDTO imageDTO) throws UserNotFoundException {
        boolean existUser = userRepository.findById(imageDTO.getUserid()).isPresent();
        if (existUser){
            Image image = imageDTO.toModel();
            User user = userRepository.findById(imageDTO.getUserid()).get();
            List<User> users = new ArrayList<>();
            users.add(user);
            image.setUsers(users);
            imageRepository.save(image);
            return new ImageDataDTO().toDTO(image);
        }else{
            throw new UserNotFoundException();
        }
    }

    @Override
    public ImageDataDTO updateImagePath(long id, String path) throws ImageNotFoundException {
        boolean existImage = imageRepository.findById(id).isPresent();
        if (existImage){
            Image image = imageRepository.findById(id).get();
            image.setPath(path);
            imageRepository.save(image);
            return new ImageDataDTO().toDTO(image);
        }else{
            throw new ImageNotFoundException();
        }
    }

    @Override
    public List<ImageDataDTO> getAllUserImages(long userid) throws UserNotFoundException {
        boolean existUser = userRepository.findById(userid).isPresent();
        if (existUser){
            User user = userRepository.findById(userid).get();
            List<Image> getAllImages = imageRepository.findAll();
            List<Image> getAllUserImages = new ArrayList<>();
            for (Image image : getAllImages){
                if (image.getUsers().contains(user)){
                    getAllUserImages.add(image);
                }
            }
            return ImageDataDTO.toDTOList(getAllUserImages);
        }else{
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteImage(long id) throws ImageNotFoundException {
        boolean existImage = imageRepository.findById(id).isPresent();
        if (existImage){
            Image image = imageRepository.findById(id).get();
            image.setUsers(Collections.emptyList());
            imageRepository.save(image);
            imageRepository.deleteById(id);
        }else{
            throw new ImageNotFoundException();
        }
    }

    @Override
    public void deleteAllUserImages(long userid) throws UserNotFoundException, ImageNotFoundException {
        boolean existUser = userRepository.findById(userid).isPresent();
        if (existUser){
            List<ImageDataDTO> getAllUserImages = getAllUserImages(userid);
            for (ImageDataDTO image : getAllUserImages){
                deleteImage(image.getId());
            }
        }else{
            throw new UserNotFoundException();
        }
    }

}
