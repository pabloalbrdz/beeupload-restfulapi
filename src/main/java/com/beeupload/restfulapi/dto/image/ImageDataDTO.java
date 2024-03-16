package com.beeupload.restfulapi.dto.image;

import com.beeupload.restfulapi.model.Image;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImageDataDTO {

    private long id;

    private String path;

    public ImageDataDTO toDTO(Image image){
        this.setId(image.getImageid());
        this.setPath(image.getPath());

        return this;
    }

    public Image toModel(){
        Image model = new Image();
        model.setImageid(this.getId());
        model.setPath(this.getPath());

        return model;
    }

    public static List<ImageDataDTO> toDTOList(List<Image> imageList){
        List<ImageDataDTO> imageDTOList = new ArrayList<>();
        for (Image image : imageList){
            imageDTOList.add(new ImageDataDTO().toDTO(image));
        }
        return imageDTOList;
    }

}
