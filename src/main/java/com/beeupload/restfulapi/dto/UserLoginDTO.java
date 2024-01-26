package com.beeupload.restfulapi.dto;

import com.beeupload.restfulapi.model.User;
import lombok.Data;

@Data
public class UserLoginDTO {

    private long id;

    private String username;

    public UserLoginDTO toDTO(User user){
        this.setId(user.getId());
        this.setUsername(user.getUsername());

        return this;
    }

    public User toModel(){
        User model = new User();
        model.setId(this.getId());
        model.setUsername(this.getUsername());

        return model;
    }

}
