package com.beeupload.restfulapi.jwt;

import com.beeupload.restfulapi.exception.NoAccessException;
import com.beeupload.restfulapi.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
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

    @Override
    public boolean verifyUserToken(long id, String token) {
        try{
            long tokenId = tokenUserId(token);
            if (id == tokenId){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    private long tokenUserId(String token){
        try{
            String[] chunks = token.split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String payload = new String(decoder.decode(chunks[1]));
            JSONObject payloadJSON = new JSONObject(payload);
            return payloadJSON.getLong("sub");
        }catch (Exception e){
            return -1;
        }
    }

}
