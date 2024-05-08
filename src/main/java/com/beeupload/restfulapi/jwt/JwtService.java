package com.beeupload.restfulapi.jwt;

import com.beeupload.restfulapi.model.User;

public interface JwtService {

    public String getToken(User user);

}
