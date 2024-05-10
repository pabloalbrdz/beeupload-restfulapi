package com.beeupload.restfulapi.jwt;

import com.beeupload.restfulapi.exception.NoAccessException;
import com.beeupload.restfulapi.model.User;

public interface JwtService {

    public String getToken(User user);

    public boolean verifyUserToken(long id, String token);

}
