package com.beeupload.restfulapi.dto.video;

import com.beeupload.restfulapi.model.Video;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VideoDataDTO {

    private long id;

    private String path;

    public VideoDataDTO toDTO(Video video){
        this.setId(video.getVideoid());
        this.setPath(video.getPath());

        return this;
    }

    public Video toModel(){
        Video model = new Video();
        model.setVideoid(this.getId());
        model.setPath(this.getPath());

        return model;
    }

    public static List<VideoDataDTO> toDTOList(List<Video> videoList){
        List<VideoDataDTO> videoDTOList = new ArrayList<>();
        for (Video video : videoList){
            videoDTOList.add(new VideoDataDTO().toDTO(video));
        }
        return videoDTOList;
    }

}
