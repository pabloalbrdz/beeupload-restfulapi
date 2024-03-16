package com.beeupload.restfulapi.dto.music;

import com.beeupload.restfulapi.model.Music;
import lombok.Data;

@Data
public class MusicDTO {

    private long id;

    private String name;

    private String artist;

    private String path;

    private long userid;

    public MusicDTO toDTO(Music music, long userid){
        this.setId(music.getMusicid());
        this.setName(music.getName());
        this.setArtist(music.getArtist());
        this.setPath(music.getPath());
        this.setUserid(userid);

        return this;
    }

    public Music toModel(){
        Music model = new Music();
        model.setMusicid(this.getId());
        model.setName(this.getName());
        model.setArtist(this.getArtist());
        model.setPath(this.getPath());

        return model;
    }

}
