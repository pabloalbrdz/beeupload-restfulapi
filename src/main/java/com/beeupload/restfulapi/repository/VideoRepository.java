package com.beeupload.restfulapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beeupload.restfulapi.model.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
}
