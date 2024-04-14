package com.beeupload.restfulapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.beeupload.restfulapi.service.DocumentService;

import com.beeupload.restfulapi.dto.document.DocumentDTO;
import com.beeupload.restfulapi.dto.document.DocumentDataDTO;

import com.beeupload.restfulapi.exception.DocumentNotFoundException;
import com.beeupload.restfulapi.exception.UserNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@CrossOrigin
@RequestMapping("/api/document")
@Tag(name = "Document", description = "Document Controller")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @PostMapping("/saveDocument")
    @Operation(summary = "Save Document")
    public ResponseEntity<?> saveDocument(@RequestBody DocumentDTO document){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(documentService.saveDocument(document));
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }
    }

    @PutMapping("/updateDocumentPath")
    @Operation(summary = "Update Document Path")
    public ResponseEntity<?> updateDocumentPath(@RequestBody DocumentDataDTO documentDataDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(documentService.updateDocumentPath(documentDataDTO.getId(), documentDataDTO.getPath()));
        }catch (DocumentNotFoundException dnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dnfe.getMessage());
        }
    }

    @GetMapping("/getAllDocumentsById/{userid}")
    @Operation(summary = "Get All Documents By User Id")
    public ResponseEntity<?> getAllUserDocuments(@PathVariable long userid){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(documentService.getAllUserDocuments(userid));
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }
    }

    @DeleteMapping("/deleteDocument/{id}")
    @Operation(summary = "Delete Document By Id")
    public ResponseEntity<?> deleteDocument(@PathVariable long id){
        try {
            documentService.deleteDocument(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (DocumentNotFoundException dnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dnfe.getMessage());
        }
    }

    @DeleteMapping("/deleteAllUserDocuments/{userid}")
    @Operation(summary = "Delete All Documents By User Id")
    public ResponseEntity<?> deleteAllUserDocuments(@PathVariable long userid){
        try {
            documentService.deleteAllUserDocuments(userid);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (UserNotFoundException unfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfe.getMessage());
        }catch (DocumentNotFoundException dnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dnfe.getMessage());
        }
    }

}
