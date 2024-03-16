package com.beeupload.restfulapi.dto.video;

import com.beeupload.restfulapi.model.Video;
import lombok.Data;

@Data
public class VideoDTO {

    private long id;

    private String name;

    private String path;

    private long userid;

    public VideoDTO toDTO(Video video, long userid){
        this.setId(video.getVideoid());
        this.setName(video.getName());
        this.setPath(video.getPath());
        this.setUserid(userid);

        return this;
    }

    public Video toModel(){
        Video model = new Video();
        model.setVideoid(this.getId());
        model.setName(this.getName());
        model.setPath(this.getPath());

        return model;
    }

}
