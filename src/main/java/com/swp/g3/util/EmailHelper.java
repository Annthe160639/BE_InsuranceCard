package com.swp.g3.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class EmailHelper {
    @Autowired
    public Crypto crypto;
    public String generateVerifyLink(String username){
        System.out.println(username);
        try {
            String link = "<a>http://localhost:8080/api/user/verify/" + crypto.encrypt(username);
            return link;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
