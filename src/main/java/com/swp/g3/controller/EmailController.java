package com.swp.g3.controller;

import com.swp.g3.entity.Customer;
import com.swp.g3.service.EmailService;
import com.swp.g3.service.CustomerService;
import com.swp.g3.util.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {
    @Autowired
    public EmailService emailService;
    @Autowired
    public CustomerService customerService;
    @Autowired
    Crypto crypto;
    @GetMapping(value = "/api/user/verify/{key}")
    public boolean verify(@PathVariable String key){
        try {
            String username = crypto.decrypt(key);
            Customer c = customerService.findOneByUsername(username);
            c.setActive(true);
            customerService.save(c);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
