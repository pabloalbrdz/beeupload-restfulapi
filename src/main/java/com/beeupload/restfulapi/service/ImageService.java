package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.image.ImageDTO;
import com.beeupload.restfulapi.dto.image.ImageDataDTO;

import com.beeupload.restfulapi.exception.ImageNotFoundException;
import com.beeupload.restfulapi.exception.NoAccessException;
import com.beeupload.restfulapi.exception.UserNotFoundException;

import java.util.List;

public interface ImageService {

    ImageDataDTO saveImage(ImageDTO imageDTO, String token) throws UserNotFoundException, NoAccessException;

    ImageDataDTO updateImagePath(long id, String path, String token) throws ImageNotFoundException, NoAccessException;

    List<ImageDataDTO> getAllUserImages(long userid, String token) throws UserNotFoundException, NoAccessException;

    void deleteImage(long id, String token) throws ImageNotFoundException, NoAccessException;

    void deleteAllUserImages(long userid, String token) throws UserNotFoundException, ImageNotFoundException, NoAccessException;

}
