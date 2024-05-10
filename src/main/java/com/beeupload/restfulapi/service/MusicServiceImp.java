package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.exception.NoAccessException;
import com.beeupload.restfulapi.jwt.JwtService;
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

    @Autowired
    JwtService jwtService;

    @Override
    public MusicDataDTO saveMusic(MusicDTO musicDTO, String token) throws UserNotFoundException, NoAccessException {
        boolean authorized = jwtService.verifyUserToken(musicDTO.getUserid(), token);
        if (authorized){
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
        }else{
            throw new NoAccessException();
        }
    }

    @Override
    public MusicDataDTO updateMusic(long id, MusicDataDTO music, String token) throws MusicNotFoundException, NoAccessException {
        boolean existMusic = musicRepository.findById(id).isPresent();
        if (existMusic){
            boolean authorized = jwtService.verifyUserToken(musicRepository.findById(id).get().getUsers().get(0).getUserid(), token);
            if(authorized){
                Music musicUpdated = musicRepository.findById(id).get();
                musicUpdated.setName(music.getName());
                musicUpdated.setArtist(music.getArtist());
                musicRepository.save(musicUpdated);
                return new MusicDataDTO().toDTO(musicUpdated);
            }else {
                throw new NoAccessException();
            }
        }else{
            throw new MusicNotFoundException();
        }
    }

    @Override
    public MusicDataDTO updateMusicPath(long id, String path, String token) throws MusicNotFoundException, NoAccessException {
        boolean existMusic = musicRepository.findById(id).isPresent();
        if (existMusic){
            boolean authorized = jwtService.verifyUserToken(musicRepository.findById(id).get().getUsers().get(0).getUserid(), token);
            if(authorized){
                Music music = musicRepository.findById(id).get();
                music.setPath(path);
                musicRepository.save(music);
                return new MusicDataDTO().toDTO(music);
            }else{
                throw new NoAccessException();
            }
        }else{
            throw new MusicNotFoundException();
        }
    }

    @Override
    public List<MusicDataDTO> getAllUserMusic(long userid, String token) throws UserNotFoundException, NoAccessException {
        boolean existUser = userRepository.findById(userid).isPresent();
        if (existUser){
            boolean authorized = jwtService.verifyUserToken(userid, token);
            if(authorized){
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
                throw new NoAccessException();
            }
        }else{
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteMusic(long id, String token) throws MusicNotFoundException, NoAccessException {
        boolean existMusic = musicRepository.findById(id).isPresent();
        if (existMusic){
            Music music = musicRepository.findById(id).get();
            boolean authorized = jwtService.verifyUserToken(musicRepository.findById(id).get().getUsers().get(0).getUserid(), token);
            if(authorized){
                music.setUsers(Collections.emptyList());
                musicRepository.save(music);
                musicRepository.deleteById(id);
            }else{
                throw new NoAccessException();
            }
        }else{
            throw new MusicNotFoundException();
        }
    }

    @Override
    public void deleteAllUserMusic(long userid, String token) throws UserNotFoundException, MusicNotFoundException, NoAccessException {
        boolean existUser = userRepository.findById(userid).isPresent();
        if (existUser){
            boolean authorized = jwtService.verifyUserToken(userid, token);
            if(authorized){
                List<MusicDataDTO> getAllUserMusic = getAllUserMusic(userid, token);
                for (MusicDataDTO music : getAllUserMusic){
                    deleteMusic(music.getId(), token);
                }
            }else{
                throw new NoAccessException();
            }
        }else{
            throw new UserNotFoundException();
        }
    }

}