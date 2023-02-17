package com.swp.g3.controller;

import com.swp.g3.entity.Customer;
import com.swp.g3.service.EmailService;
import com.swp.g3.service.UserService;
import com.swp.g3.util.Crypto;
import com.swp.g3.util.EmailHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.validation.Valid;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

@RestController
public class EmailController {
    @Autowired
    public EmailService emailService;
    @Autowired
    public UserService userService;
    @Autowired
    Crypto crypto;
    @GetMapping(value = "/api/user/verify/{key}")
    public boolean verify(@PathVariable String key){
        try {
            String username = crypto.decrypt(key);
            Customer c = userService.findOneByUsername(username);
            c.setActive(true);
            userService.save(c);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
