package com.beeupload.restfulapi.service;

import com.beeupload.restfulapi.dto.document.DocumentDTO;
import com.beeupload.restfulapi.dto.document.DocumentDataDTO;
import com.beeupload.restfulapi.exception.DocumentNotFoundException;
import com.beeupload.restfulapi.exception.UserNotFoundException;
import com.beeupload.restfulapi.model.Document;
import com.beeupload.restfulapi.model.User;
import com.beeupload.restfulapi.repository.DocumentRepository;
import com.beeupload.restfulapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DocumentServiceImp implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public DocumentDataDTO saveDocument(DocumentDTO documentDTO) throws UserNotFoundException {
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
    }

    @Override
    public List<DocumentDataDTO> getAllUserDocuments(long userid) throws UserNotFoundException {
        boolean existUser = userRepository.findById(userid).isPresent();
        if (existUser){
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
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteDocument(long id) throws DocumentNotFoundException {
        boolean existDocument = documentRepository.findById(id).isPresent();
        if (existDocument){
            Document document = documentRepository.findById(id).get();
            document.setUsers(Collections.emptyList());
            documentRepository.save(document);
            documentRepository.deleteById(id);
        }else{
            throw new DocumentNotFoundException();
        }
    }

    @Override
    public void deleteAllUserDocuments(long userid) throws UserNotFoundException, DocumentNotFoundException {
        boolean existUser = userRepository.findById(userid).isPresent();
        if (existUser){
            List<DocumentDataDTO> getAllUserDocuments = getAllUserDocuments(userid);
            for (DocumentDataDTO document : getAllUserDocuments){
                deleteDocument(document.getId());
            }
        }else{
            throw new UserNotFoundException();
        }
    }

}
