package com.beeupload.restfulapi.dto.music;

import com.beeupload.restfulapi.model.Music;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MusicDataDTO {

    private long id;

    private String name;

    private String artist;

    private String path;

    public MusicDataDTO toDTO(Music music){
        this.setId(music.getMusicid());
        this.setName(music.getName());
        this.setArtist(music.getArtist());
        this.setPath(music.getPath());

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

    public static List<MusicDataDTO> toDTOList(List<Music> musicList){
        List<MusicDataDTO> musicDataDTOList = new ArrayList<>();
        for (Music music : musicList){
            musicDataDTOList.add(new MusicDataDTO().toDTO(music));
        }
        return musicDataDTOList;
    }

}
