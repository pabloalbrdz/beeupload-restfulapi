package com.beeupload.restfulapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beeupload.restfulapi.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
