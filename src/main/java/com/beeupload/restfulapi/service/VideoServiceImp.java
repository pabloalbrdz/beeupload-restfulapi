package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.exception.NoAccessException;
import com.beeupload.restfulapi.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beeupload.restfulapi.model.User;
import com.beeupload.restfulapi.model.Video;

import com.beeupload.restfulapi.dto.video.VideoDTO;
import com.beeupload.restfulapi.dto.video.VideoDataDTO;

import com.beeupload.restfulapi.exception.UserNotFoundException;
import com.beeupload.restfulapi.exception.VideoNotFoundException;

import com.beeupload.restfulapi.repository.UserRepository;
import com.beeupload.restfulapi.repository.VideoRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VideoServiceImp implements VideoService {

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Override
    public VideoDataDTO saveVideo(VideoDTO videoDTO, String token) throws UserNotFoundException, NoAccessException {
        boolean authorized = jwtService.verifyUserToken(videoDTO.getUserid(), token);
        if(authorized){
            boolean existUser = userRepository.findById(videoDTO.getUserid()).isPresent();
            if (existUser){
                Video video = videoDTO.toModel();
                User user = userRepository.findById(videoDTO.getUserid()).get();
                List<User> users = new ArrayList<>();
                users.add(user);
                video.setUsers(users);
                videoRepository.save(video);
                return new VideoDataDTO().toDTO(video);
            }else{
                throw new UserNotFoundException();
            }
        }else{
            throw new NoAccessException();
        }
    }

    @Override
    public VideoDataDTO updateVideo(long id, VideoDataDTO video, String token) throws VideoNotFoundException, NoAccessException {
        boolean existVideo = videoRepository.findById(id).isPresent();
        if (existVideo){
            boolean authorized = jwtService.verifyUserToken(videoRepository.findById(id).get().getUsers().get(0).getUserid(), token);
            if(authorized){
                Video videoUpdated = videoRepository.findById(id).get();
                videoUpdated.setName(video.getName());
                videoRepository.save(videoUpdated);
                return new VideoDataDTO().toDTO(videoUpdated);
            }else{
                throw new NoAccessException();
            }
        }else{
            throw new VideoNotFoundException();
        }
    }

    @Override
    public VideoDataDTO updateVideoPath(long id, String path, String token) throws VideoNotFoundException, NoAccessException {
        boolean existVideo = videoRepository.findById(id).isPresent();
        if (existVideo){
            boolean authorized = jwtService.verifyUserToken(videoRepository.findById(id).get().getUsers().get(0).getUserid(), token);
            if(authorized){
                Video video = videoRepository.findById(id).get();
                video.setPath(path);
                videoRepository.save(video);
                return new VideoDataDTO().toDTO(video);
            }else {
                throw new NoAccessException();
            }
        }else{
            throw new VideoNotFoundException();
        }
    }

    @Override
    public List<VideoDataDTO> getAllUserVideos(long userid, String token) throws UserNotFoundException, NoAccessException {
        boolean existUser = userRepository.findById(userid).isPresent();
        if (existUser){
            boolean authorized = jwtService.verifyUserToken(userid, token);
            if(authorized){
                User user = userRepository.findById(userid).get();
                List<Video> getAllVideos = videoRepository.findAll();
                List<Video> getAllUserVideos = new ArrayList<>();
                for (Video video : getAllVideos){
                    if (video.getUsers().contains(user)){
                        getAllUserVideos.add(video);
                    }
                }
                return VideoDataDTO.toDTOList(getAllUserVideos);
            }else{
                throw new NoAccessException();
            }
        }else{
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteVideo(long id, String token) throws VideoNotFoundException, NoAccessException {
        boolean existVideo = videoRepository.findById(id).isPresent();
        if (existVideo){
            Video video = videoRepository.findById(id).get();
            boolean authorized = jwtService.verifyUserToken(videoRepository.findById(id).get().getUsers().get(0).getUserid(), token);
            if(authorized){
                video.setUsers(Collections.emptyList());
                videoRepository.save(video);
                videoRepository.deleteById(id);
            }else{
                throw new NoAccessException();
            }
        }else{
            throw new VideoNotFoundException();
        }
    }

    @Override
    public void deleteAllUserVideos(long userid, String token) throws UserNotFoundException, VideoNotFoundException, NoAccessException {
        boolean existUser = userRepository.findById(userid).isPresent();
        if (existUser){
            boolean authorized = jwtService.verifyUserToken(userid, token);
            if(authorized){
                List<VideoDataDTO> getAllUserVideo = getAllUserVideos(userid, token);
                for (VideoDataDTO video : getAllUserVideo){
                    deleteVideo(video.getId(), token);
                }
            }else{
                throw new NoAccessException();
            }
        }else{
            throw new UserNotFoundException();
        }
    }

}
