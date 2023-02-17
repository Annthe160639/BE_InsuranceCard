package com.swp.g3.controller;

import com.swp.g3.entity.Customer;
import com.swp.g3.service.EmailService;
import com.swp.g3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;
    @PostMapping(value = "/api/user/register")
    public boolean register(@Valid @RequestBody Customer customer){
        try{
            userService.save(customer);
            emailService.sendEmail(customer.getGmail(), customer.getUsername(), customer.getName());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    @GetMapping(value = "/api/user/username/duplicate/{username}")
    public boolean checkExistedUsername(@PathVariable String username){
        return userService.findOneByUsername(username) != null;
    }

    @GetMapping(value = "/api/user/gmail/duplicate/{gmail}")
    public boolean checkExistedEmail(@PathVariable String gmail){
        return userService.findOneByGmail(gmail) != null;
    }
}
