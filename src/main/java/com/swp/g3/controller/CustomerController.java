package com.swp.g3.controller;

import com.swp.g3.entity.Customer;
import com.swp.g3.service.EmailService;
import com.swp.g3.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    EmailService emailService;
    @PostMapping(value = "/api/customer/register")
    public boolean register(@Valid @RequestBody Customer customer){
        try{
            customerService.save(customer);
            emailService.sendEmail(customer.getGmail(), customer.getUsername(), customer.getName());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    @GetMapping(value = "/api/customer/username/duplicate/{username}")
    public boolean checkExistedUsername(@PathVariable String username){
        return customerService.findOneByUsername(username) != null;
    }

    @GetMapping(value = "/api/customer/gmail/duplicate/{gmail}")
    public boolean checkExistedEmail(@PathVariable String gmail){
        return customerService.findOneByGmail(gmail) != null;
    }

    @GetMapping(value = "/api/customer/username/list")
    public Page<Customer> listCustomer (@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                        @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                        @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort)
    {

        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("id").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("id").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        Page<Customer> p = customerService.findCustomers(pageable);
        return p;
    }




}
