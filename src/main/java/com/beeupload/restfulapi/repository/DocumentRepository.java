package com.beeupload.restfulapi.repository;

import com.beeupload.restfulapi.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
