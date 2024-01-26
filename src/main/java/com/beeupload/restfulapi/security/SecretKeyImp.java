package com.beeupload.restfulapi.security;

import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

@Service
public class SecretKeyImp implements SecretKey {

    @Override
    public SecretKeySpec createSecretKey(String password) throws Exception {
        byte[] bytesPwd = password.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        bytesPwd = md.digest(bytesPwd);
        bytesPwd = Arrays.copyOf(bytesPwd, 16);
        SecretKeySpec sks = new SecretKeySpec(bytesPwd, "AES");
        return sks;
    }

}
