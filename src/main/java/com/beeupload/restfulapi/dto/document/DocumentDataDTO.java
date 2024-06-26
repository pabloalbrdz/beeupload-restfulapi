package com.beeupload.restfulapi.dto.document;

import com.beeupload.restfulapi.model.Document;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class DocumentDataDTO implements Serializable {

    private long id;

    private String name;

    private String path;

    public DocumentDataDTO toDTO(Document document){
        this.setId(document.getDocumentid());
        this.setName(document.getName());
        this.setPath(document.getPath());

        return this;
    }

    public Document toModel(){
        Document model = new Document();
        model.setDocumentid(this.getId());
        model.setName(this.getName());
        model.setPath(this.getPath());

        return model;
    }

    public static List<DocumentDataDTO> toDTOList(List<Document> documentList){
        List<DocumentDataDTO> documentDataDTOList = new ArrayList<>();
        for (Document document : documentList){
            documentDataDTOList.add(new DocumentDataDTO().toDTO(document));
        }
        return documentDataDTOList;
    }

}
