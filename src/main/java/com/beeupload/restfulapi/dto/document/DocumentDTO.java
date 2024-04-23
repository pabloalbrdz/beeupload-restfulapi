package com.beeupload.restfulapi.dto.document;


import com.beeupload.restfulapi.model.Document;

import lombok.Data;

import java.io.Serializable;

@Data
public class DocumentDTO implements Serializable {

    private long id;

    private String name;

    private String path;

    private long userid;

    public DocumentDTO toDTO(Document document, long userid){
        this.setId(document.getDocumentid());
        this.setName(document.getName());
        this.setPath(document.getPath());
        this.setUserid(userid);

        return this;
    }

    public Document toModel(){
        Document model = new Document();
        model.setDocumentid(this.getId());
        model.setName(this.getName());
        model.setPath(this.getPath());

        return model;
    }

}
