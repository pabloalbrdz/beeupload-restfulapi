package com.beeupload.restfulapi.dto.user;

import com.beeupload.restfulapi.model.User;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginDTO implements Serializable {

    private String username;

    private String password;

    public UserLoginDTO toDTO(User user){
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());

        return this;
    }

    public User toModel(){
        User model = new User();
        model.setUsername(this.getUsername());
        model.setPassword(this.getPassword());

        return model;
    }

}
