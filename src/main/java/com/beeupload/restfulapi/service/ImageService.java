package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.image.ImageDTO;
import com.beeupload.restfulapi.dto.image.ImageDataDTO;
import com.beeupload.restfulapi.exception.ImageNotFoundException;
import com.beeupload.restfulapi.exception.UserNotFoundException;

import java.util.List;

public interface ImageService {

    ImageDataDTO saveImage(ImageDTO imageDTO) throws UserNotFoundException;

    List<ImageDataDTO> getAllUserImages(long userid) throws UserNotFoundException;

    void deleteImage(long id) throws ImageNotFoundException;

    void deleteAllUserImages(long userid) throws UserNotFoundException, ImageNotFoundException;

}
