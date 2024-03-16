package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.image.ImageDTO;
import com.beeupload.restfulapi.dto.image.ImageDataDTO;
import com.beeupload.restfulapi.exception.UserNotFoundException;

public interface ImageService {

    ImageDataDTO saveImage(ImageDTO imageDTO) throws UserNotFoundException;

}
