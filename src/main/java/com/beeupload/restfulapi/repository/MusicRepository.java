package com.beeupload.restfulapi.repository;

import com.beeupload.restfulapi.model.Document;
import com.beeupload.restfulapi.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
