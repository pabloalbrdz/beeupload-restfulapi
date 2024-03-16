package com.beeupload.restfulapi.dto.image;

import com.beeupload.restfulapi.model.Image;
import lombok.Data;

@Data
public class ImageDTO {

    private long id;

    private String path;

    private long userid;

    public ImageDTO toDTO(Image image, long userid){
        this.setId(image.getImageid());
        this.setPath(image.getPath());
        this.setUserid(userid);

        return this;
    }

    public Image toModel(){
        Image model = new Image();
        model.setImageid(this.getId());
        model.setPath(this.getPath());

        return model;
    }

}
