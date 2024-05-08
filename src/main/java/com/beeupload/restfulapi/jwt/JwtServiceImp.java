package com.beeupload.restfulapi.jwt;

import com.beeupload.restfulapi.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImp implements JwtService {

    private final String SECRET_KEY = "UyZd9nEaTbT8PbGkNpRr4UvYxZr4AwGqKtNtWkMbYd3VgZdCfRqUv2Yn5Rs3SxVr";

    @Override
    public String getToken(User user) {
        return tokenBuilder(new HashMap<>(), user);
    }

    private String tokenBuilder(Map<String, Object> extraClaims, User user){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(String.valueOf(user.getUserid()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(){
        byte[] getBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(getBytes);
    }

}
