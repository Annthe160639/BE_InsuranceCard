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
    @GetMapping(value = "/api/customer/verify/{key}")
    public boolean verify(@PathVariable String key){
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
    @PostMapping(value = "/api/customer/password/reset/{key}")
    public String resetPassword(@PathVariable String key, @RequestParam(name = "password") String password, @RequestParam(name = "password2") String password2){
        if(password.equals(password2)) {
            try {

                String username = crypto.decrypt(key).split("\\|")[0];
                Customer c = customerService.findOneByUsername(username);
                c.setPassword(password);
                customerService.save(c);
                return "Đặt lại mật khẩu thành công.";
            } catch (Exception e) {
                e.printStackTrace();
                return "Đặt lại mật khẩu không thành công.";
            }
        }else {
            return "Mật khẩu không chính xác.";
        }
    }
}
