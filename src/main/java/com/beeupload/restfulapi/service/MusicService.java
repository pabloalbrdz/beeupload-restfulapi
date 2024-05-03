package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.music.MusicDTO;
import com.beeupload.restfulapi.dto.music.MusicDataDTO;

import com.beeupload.restfulapi.exception.MusicNotFoundException;
import com.beeupload.restfulapi.exception.UserNotFoundException;

import java.util.List;

public interface MusicService {

    MusicDataDTO saveMusic(MusicDTO musicDTO) throws UserNotFoundException;

    MusicDataDTO updateMusic(long id, MusicDataDTO music) throws MusicNotFoundException;

    MusicDataDTO updateMusicPath(long id, String path) throws MusicNotFoundException;

    List<MusicDataDTO> getAllUserMusic(long userid) throws UserNotFoundException;

    void deleteMusic(long id) throws MusicNotFoundException;

    void deleteAllUserMusic(long userid) throws UserNotFoundException, MusicNotFoundException;

}
