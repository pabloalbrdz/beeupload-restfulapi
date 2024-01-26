package com.beeupload.restfulapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class PasswordEncryptImp implements PasswordEncrypt {

    @Autowired
    private SecretKey secretKey;

    @Override
    public String encrypt(String passwordToEncrypt) throws Exception {
        SecretKeySpec sks = secretKey.createSecretKey(passwordToEncrypt);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sks);
        byte[] bytesPwd = passwordToEncrypt.getBytes(StandardCharsets.UTF_8);
        byte[] bytesEncrypt = cipher.doFinal(bytesPwd);
        return Base64.getEncoder().encodeToString(bytesEncrypt);
    }

}
