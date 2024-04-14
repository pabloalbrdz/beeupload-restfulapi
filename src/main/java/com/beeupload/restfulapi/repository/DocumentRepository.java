package com.beeupload.restfulapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beeupload.restfulapi.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
