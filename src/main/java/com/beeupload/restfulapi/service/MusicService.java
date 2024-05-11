package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.music.MusicDTO;
import com.beeupload.restfulapi.dto.music.MusicDataDTO;

import com.beeupload.restfulapi.exception.MusicNotFoundException;
import com.beeupload.restfulapi.exception.NoAccessException;
import com.beeupload.restfulapi.exception.UserNotFoundException;

import java.util.List;

public interface MusicService {

    MusicDataDTO saveMusic(MusicDTO musicDTO, String token) throws UserNotFoundException, NoAccessException;

    MusicDataDTO updateMusic(long id, MusicDataDTO music, String token) throws MusicNotFoundException, NoAccessException;

    MusicDataDTO updateMusicPath(long id, String path, String token) throws MusicNotFoundException, NoAccessException;

    List<MusicDataDTO> getAllUserMusic(long userid, String token) throws UserNotFoundException, NoAccessException;

    void deleteMusic(long id, String token) throws MusicNotFoundException, NoAccessException;

    void deleteAllUserMusic(long userid, String token) throws UserNotFoundException, MusicNotFoundException, NoAccessException;

}
