package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.image.ImageDTO;
import com.beeupload.restfulapi.dto.image.ImageDataDTO;
import com.beeupload.restfulapi.exception.UserNotFoundException;
import com.beeupload.restfulapi.model.Image;
import com.beeupload.restfulapi.model.User;
import com.beeupload.restfulapi.repository.ImageRepository;
import com.beeupload.restfulapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

}
