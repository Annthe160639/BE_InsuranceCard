package com.swp.g3.controller;

import com.swp.g3.entity.Customer;
import com.swp.g3.service.EmailService;
import com.swp.g3.service.CustomerService;
import com.swp.g3.util.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.net.URLEncoder;

@RestController
public class EmailController {
    @Autowired
    public EmailService emailService;
    @Autowired
    public CustomerService customerService;
    @Autowired
    Crypto crypto;
    @PostMapping(value = "/api/customer/verify")
    public boolean verify(@RequestParam(name = "key") String key){
        try {
            String username = crypto.decrypt(key).split("\\|")[0];
            Customer c = customerService.findOneByUsername(username);
            c.setActive(true);
            customerService.save(c);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @PostMapping(value = "/api/customer/password/new")
    public ResponseEntity<?> resetPassword(@RequestParam(name = "key") String key, @RequestParam(name = "password") String password, @RequestParam(name = "password2") String password2){
        if(password.equals(password2)) {
            try {
                String username = crypto.decrypt(key).split("\\|")[0];
                Customer c = customerService.findOneByUsername(username);
                c.setPassword(crypto.encrypt(password));
                customerService.save(c);
                return ResponseEntity.ok("Đặt lại mật khẩu thành công.");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body("Đặt lại mật khẩu không thành công.");
            }
        }else {
            return ResponseEntity.badRequest().body("Mật khẩu không chính xác.");
        }
    }
}
