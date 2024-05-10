package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.exception.NoAccessException;
import com.beeupload.restfulapi.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beeupload.restfulapi.model.Document;
import com.beeupload.restfulapi.model.User;

import com.beeupload.restfulapi.dto.document.DocumentDTO;
import com.beeupload.restfulapi.dto.document.DocumentDataDTO;

import com.beeupload.restfulapi.exception.DocumentNotFoundException;
import com.beeupload.restfulapi.exception.UserNotFoundException;

import com.beeupload.restfulapi.repository.DocumentRepository;
import com.beeupload.restfulapi.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DocumentServiceImp implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Override
    public DocumentDataDTO saveDocument(DocumentDTO documentDTO, String token) throws UserNotFoundException, NoAccessException {
        boolean authorized = jwtService.verifyUserToken(documentDTO.getUserid(), token);
        if (authorized){
            boolean existUser = userRepository.findById(documentDTO.getUserid()).isPresent();
            if (existUser){
                Document document = documentDTO.toModel();
                User user = userRepository.findById(documentDTO.getUserid()).get();
                List<User> users = new ArrayList<>();
                users.add(user);
                document.setUsers(users);
                documentRepository.save(document);
                return new DocumentDataDTO().toDTO(document);
            }else{
                throw new UserNotFoundException();
            }
        }else {
            throw new NoAccessException();
        }
    }

    @Override
    public DocumentDataDTO updateDocument(long id, DocumentDataDTO document, String token) throws DocumentNotFoundException, NoAccessException {
        boolean existDocument = documentRepository.findById(id).isPresent();
        if (existDocument){
            boolean authorized = jwtService.verifyUserToken(documentRepository.findById(id).get().getUsers().get(0).getUserid(), token);
            if(authorized){
                Document documentUpdated = documentRepository.findById(id).get();
                documentUpdated.setName(document.getName());
                documentRepository.save(documentUpdated);
                return new DocumentDataDTO().toDTO(documentUpdated);
            }else{
                throw new NoAccessException();
            }
        }else {
            throw new DocumentNotFoundException();
        }
    }

    @Override
    public DocumentDataDTO updateDocumentPath(long id, String path, String token) throws DocumentNotFoundException, NoAccessException {
        boolean existDocument = documentRepository.findById(id).isPresent();
        if (existDocument){
            boolean authorized = jwtService.verifyUserToken(documentRepository.findById(id).get().getUsers().get(0).getUserid(), token);
            if (authorized){
                Document document = documentRepository.findById(id).get();
                document.setPath(path);
                documentRepository.save(document);
                return new DocumentDataDTO().toDTO(document);
            }else{
                throw new NoAccessException();
            }
        }else {
            throw new DocumentNotFoundException();
        }
    }

    @Override
    public List<DocumentDataDTO> getAllUserDocuments(long userid, String token) throws UserNotFoundException, NoAccessException {
        boolean existUser = userRepository.findById(userid).isPresent();
        if (existUser){
            boolean authorized = jwtService.verifyUserToken(userid, token);
            if (authorized){
                User user = userRepository.findById(userid).get();
                List<Document> getAllDocuments = documentRepository.findAll();
                List<Document> getAllUserDocuments = new ArrayList<>();
                for (Document document : getAllDocuments){
                    if (document.getUsers().contains(user)){
                        getAllUserDocuments.add(document);
                    }
                }
                return DocumentDataDTO.toDTOList(getAllUserDocuments);
            }else{
                throw new NoAccessException();
            }
        }else{
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteDocument(long id, String token) throws DocumentNotFoundException, NoAccessException {
        boolean existDocument = documentRepository.findById(id).isPresent();
        if (existDocument){
            Document document = documentRepository.findById(id).get();
            boolean authorized = jwtService.verifyUserToken(document.getUsers().get(0).getUserid(), token);
            if (authorized){
                document.setUsers(Collections.emptyList());
                documentRepository.save(document);
                documentRepository.deleteById(id);
            }else{
                throw new NoAccessException();
            }
        }else{
            throw new DocumentNotFoundException();
        }
    }

    @Override
    public void deleteAllUserDocuments(long userid, String token) throws UserNotFoundException, DocumentNotFoundException, NoAccessException {
        boolean existUser = userRepository.findById(userid).isPresent();
        if (existUser){
            boolean authorized = jwtService.verifyUserToken(userid, token);
            if (authorized){
                List<DocumentDataDTO> getAllUserDocuments = getAllUserDocuments(userid, token);
                for (DocumentDataDTO document : getAllUserDocuments){
                    deleteDocument(document.getId(), token);
                }
            }else{
                throw new NoAccessException();
            }
        }else{
            throw new UserNotFoundException();
        }
    }

}
