package com.swp.g3.controllers;

import com.swp.g3.entity.Customer;
import com.swp.g3.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

//    @PostMapping(value = "/api/user/register")
//    public boolean register(@Valid @RequestBody Customer customer){
//        try{
//            userService.save(customer);
//            emailService.sendEmail(customer.getGmail(), customer.getUsername(), customer.getName());
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//    @GetMapping(value = "/api/user/username/duplicate/{username}")
//    public boolean checkExistedUsername(@PathVariable String username){
//        return userService.findOneByUsername(username) != null;
//    }
//
//    @GetMapping(value = "/api/user/gmail/duplicate/{gmail}")
//    public boolean checkExistedEmail(@PathVariable String gmail){
//        return userService.findOneByGmail(gmail) != null;
//    }

    @GetMapping(value = "api/customer/detail/{id}")
    public Customer CustomerDetail(@PathVariable Integer id){
        return customerService.findOneById(id);
    }




}



