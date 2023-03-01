package com.swp.g3.controller;

import com.swp.g3.entity.*;
import com.swp.g3.repository.CustomerRepository;
import com.swp.g3.repository.ManagerRepository;
import com.swp.g3.service.CustomerService;
import com.swp.g3.service.ManagerService;
import com.swp.g3.util.Crypto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class ManagerController {
    @Autowired
    ManagerService managerService;
    @Autowired
    Crypto crypto;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    CustomerService customerService;
    @GetMapping(value = "/api/manager/customer/list")
    public Page<Customer> listCustomer(HttpSession session,
                                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                       @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                       @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {

        Manager manager = (Manager) session.getAttribute("manager");
        if (manager == null) {
            return null;
        }
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
