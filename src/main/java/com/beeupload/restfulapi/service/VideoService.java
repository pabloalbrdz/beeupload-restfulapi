package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.video.VideoDTO;
import com.beeupload.restfulapi.dto.video.VideoDataDTO;
import com.beeupload.restfulapi.exception.UserNotFoundException;
import com.beeupload.restfulapi.exception.VideoNotFoundException;

import java.util.List;

public interface VideoService {

    VideoDataDTO saveVideo(VideoDTO videoDTO) throws UserNotFoundException;

    List<VideoDataDTO> getAllUserVideos(long userid) throws UserNotFoundException;

    void deleteVideo(long id) throws VideoNotFoundException;

    void deleteAllUserVideos(long userid) throws UserNotFoundException, VideoNotFoundException;
}
