package com.beeupload.restfulapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beeupload.restfulapi.model.Music;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
}
