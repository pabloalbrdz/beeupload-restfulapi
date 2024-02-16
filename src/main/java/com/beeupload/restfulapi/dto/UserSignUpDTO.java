package com.beeupload.restfulapi.dto;

import com.beeupload.restfulapi.model.User;
import lombok.Data;

@Data
public class UserSignUpDTO {

    private long id;

    private String username;

    private String email;

    private String password;

    public UserSignUpDTO toDTO(User user){
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());

        return this;
    }

    public User toModel(){
        User model = new User();
        model.setId(this.getId());
        model.setUsername(this.getUsername());
        model.setEmail(this.getEmail());
        model.setPassword(this.getPassword());

        return model;
    }

}
