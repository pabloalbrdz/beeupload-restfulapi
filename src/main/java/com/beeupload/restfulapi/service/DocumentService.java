package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.document.DocumentDTO;
import com.beeupload.restfulapi.dto.document.DocumentDataDTO;

import com.beeupload.restfulapi.exception.DocumentNotFoundException;
import com.beeupload.restfulapi.exception.NoAccessException;
import com.beeupload.restfulapi.exception.UserNotFoundException;

import java.util.List;

public interface DocumentService {

    DocumentDataDTO saveDocument(DocumentDTO documentDTO, String token) throws UserNotFoundException, NoAccessException;

    DocumentDataDTO updateDocument(long id, DocumentDataDTO document, String token) throws DocumentNotFoundException, NoAccessException;

    DocumentDataDTO updateDocumentPath(long id, String path, String token) throws DocumentNotFoundException, NoAccessException;

    List<DocumentDataDTO> getAllUserDocuments(long userid, String token) throws UserNotFoundException, NoAccessException;

    void deleteDocument(long id, String token) throws DocumentNotFoundException, NoAccessException;

    void deleteAllUserDocuments(long userid, String token) throws UserNotFoundException, DocumentNotFoundException, NoAccessException;


}
