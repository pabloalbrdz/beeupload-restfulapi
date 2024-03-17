package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.document.DocumentDTO;
import com.beeupload.restfulapi.dto.document.DocumentDataDTO;
import com.beeupload.restfulapi.exception.DocumentNotFoundException;
import com.beeupload.restfulapi.exception.UserNotFoundException;

import java.util.List;

public interface DocumentService {

    DocumentDataDTO saveDocument(DocumentDTO documentDTO) throws UserNotFoundException;

    DocumentDataDTO updateDocumentPath(long id, String path) throws DocumentNotFoundException;

    List<DocumentDataDTO> getAllUserDocuments(long userid) throws UserNotFoundException;

    void deleteDocument(long id) throws DocumentNotFoundException;

    void deleteAllUserDocuments(long userid) throws UserNotFoundException, DocumentNotFoundException;


}
