package com.beeupload.restfulapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beeupload.restfulapi.model.Music;
import com.beeupload.restfulapi.model.User;

import com.beeupload.restfulapi.dto.music.MusicDTO;
import com.beeupload.restfulapi.dto.music.MusicDataDTO;

import com.beeupload.restfulapi.exception.MusicNotFoundException;
import com.beeupload.restfulapi.exception.UserNotFoundException;

import com.beeupload.restfulapi.repository.MusicRepository;
import com.beeupload.restfulapi.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MusicServiceImp implements MusicService {

    @Autowired
    MusicRepository musicRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public MusicDataDTO saveMusic(MusicDTO musicDTO) throws UserNotFoundException {
        boolean existUser = userRepository.findById(musicDTO.getUserid()).isPresent();
        if (existUser){
            Music music = musicDTO.toModel();
            User user = userRepository.findById(musicDTO.getUserid()).get();
            List<User> users = new ArrayList<>();
            users.add(user);
            music.setUsers(users);
            musicRepository.save(music);
            return new MusicDataDTO().toDTO(music);
        }else{
            throw new UserNotFoundException();
        }
    }

    @Override
    public MusicDataDTO updateMusic(long id, MusicDataDTO music) throws MusicNotFoundException {
        boolean existMusic = musicRepository.findById(id).isPresent();
        if (existMusic){
            Music musicUpdated = musicRepository.findById(id).get();
            music.setName(music.getName());
            music.setArtist(music.getArtist());
            musicRepository.save(musicUpdated);
            return new MusicDataDTO().toDTO(musicUpdated);
        }else{
            throw new MusicNotFoundException();
        }
    }

    @Override
    public MusicDataDTO updateMusicPath(long id, String path) throws MusicNotFoundException {
        boolean existMusic = musicRepository.findById(id).isPresent();
        if (existMusic){
            Music music = musicRepository.findById(id).get();
            music.setPath(path);
            musicRepository.save(music);
            return new MusicDataDTO().toDTO(music);
        }else{
            throw new MusicNotFoundException();
        }
    }

    @Override
    public List<MusicDataDTO> getAllUserMusic(long userid) throws UserNotFoundException {
        boolean existUser = userRepository.findById(userid).isPresent();
        if (existUser){
            User user = userRepository.findById(userid).get();
            List<Music> getAllMusic = musicRepository.findAll();
            List<Music> getAllUserMusic = new ArrayList<>();
            for (Music music : getAllMusic){
                if (music.getUsers().contains(user)){
                    getAllUserMusic.add(music);
                }
            }
            return MusicDataDTO.toDTOList(getAllUserMusic);
        }else{
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteMusic(long id) throws MusicNotFoundException {
        boolean existMusic = musicRepository.findById(id).isPresent();
        if (existMusic){
            Music music = musicRepository.findById(id).get();
            music.setUsers(Collections.emptyList());
            musicRepository.save(music);
            musicRepository.deleteById(id);
        }else{
            throw new MusicNotFoundException();
        }
    }

    @Override
    public void deleteAllUserMusic(long userid) throws UserNotFoundException, MusicNotFoundException {
        boolean existUser = userRepository.findById(userid).isPresent();
        if (existUser){
            List<MusicDataDTO> getAllUserMusic = getAllUserMusic(userid);
            for (MusicDataDTO music : getAllUserMusic){
                deleteMusic(music.getId());
            }
        }else{
            throw new UserNotFoundException();
        }
    }

}