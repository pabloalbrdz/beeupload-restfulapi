package com.beeupload.restfulapi.security;

import javax.crypto.spec.SecretKeySpec;

public interface SecretKey {

    SecretKeySpec createSecretKey(String password) throws Exception;

}
