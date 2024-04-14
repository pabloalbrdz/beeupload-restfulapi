package com.beeupload.restfulapi.dto.user;

import com.beeupload.restfulapi.model.User;

import lombok.Data;

@Data
public class UserDTO {

    private long id;

    public UserDTO toDTO(User user){
        this.setId(user.getUserid());

        return this;
    }

    public User toModel(){
        User model = new User();
        model.setUserid(this.getId());

        return model;
    }

}
