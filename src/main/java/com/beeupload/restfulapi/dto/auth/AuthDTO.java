package com.beeupload.restfulapi.dto.auth;

import com.beeupload.restfulapi.model.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthDTO implements Serializable {

    private long id;

    private String token;

    public AuthDTO toDTO(User user, String token){
        this.setId(user.getUserid());
        this.setToken(token);
        return this;
    }

}
