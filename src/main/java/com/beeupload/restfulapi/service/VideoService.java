package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.video.VideoDTO;
import com.beeupload.restfulapi.dto.video.VideoDataDTO;

import com.beeupload.restfulapi.exception.NoAccessException;
import com.beeupload.restfulapi.exception.UserNotFoundException;
import com.beeupload.restfulapi.exception.VideoNotFoundException;

import java.util.List;

public interface VideoService {

    VideoDataDTO saveVideo(VideoDTO videoDTO, String token) throws UserNotFoundException, NoAccessException;

    VideoDataDTO updateVideo(long id, VideoDataDTO video, String token) throws VideoNotFoundException, NoAccessException;

    VideoDataDTO updateVideoPath(long id, String path, String token) throws VideoNotFoundException, NoAccessException;

    List<VideoDataDTO> getAllUserVideos(long userid, String token) throws UserNotFoundException, NoAccessException;

    void deleteVideo(long id, String token) throws VideoNotFoundException, NoAccessException;

    void deleteAllUserVideos(long userid, String token) throws UserNotFoundException, VideoNotFoundException, NoAccessException;
}
