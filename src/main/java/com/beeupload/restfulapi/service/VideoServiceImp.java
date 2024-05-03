package com.beeupload.restfulapi.service;

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

    @Override
    public VideoDataDTO saveVideo(VideoDTO videoDTO) throws UserNotFoundException {
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
    }

    @Override
    public VideoDataDTO updateVideo(long id, VideoDataDTO video) throws VideoNotFoundException {
        boolean existVideo = videoRepository.findById(id).isPresent();
        if (existVideo){
            Video videoUpdated = videoRepository.findById(id).get();
            videoUpdated.setName(video.getName());
            videoRepository.save(videoUpdated);
            return new VideoDataDTO().toDTO(videoUpdated);
        }else{
            throw new VideoNotFoundException();
        }
    }

    @Override
    public VideoDataDTO updateVideoPath(long id, String path) throws VideoNotFoundException {
        boolean existVideo = videoRepository.findById(id).isPresent();
        if (existVideo){
            Video video = videoRepository.findById(id).get();
            video.setPath(path);
            videoRepository.save(video);
            return new VideoDataDTO().toDTO(video);
        }else{
            throw new VideoNotFoundException();
        }
    }

    @Override
    public List<VideoDataDTO> getAllUserVideos(long userid) throws UserNotFoundException {
        boolean existUser = userRepository.findById(userid).isPresent();
        if (existUser){
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
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteVideo(long id) throws VideoNotFoundException {
        boolean existVideo = videoRepository.findById(id).isPresent();
        if (existVideo){
            Video video = videoRepository.findById(id).get();
            video.setUsers(Collections.emptyList());
            videoRepository.save(video);
            videoRepository.deleteById(id);
        }else{
            throw new VideoNotFoundException();
        }
    }

    @Override
    public void deleteAllUserVideos(long userid) throws UserNotFoundException, VideoNotFoundException {
        boolean existUser = userRepository.findById(userid).isPresent();
        if (existUser){
            List<VideoDataDTO> getAllUserVideo = getAllUserVideos(userid);
            for (VideoDataDTO video : getAllUserVideo){
                deleteVideo(video.getId());
            }
        }else{
            throw new UserNotFoundException();
        }
    }

}
