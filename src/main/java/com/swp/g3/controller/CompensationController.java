package com.swp.g3.controller;

import com.swp.g3.entity.Compensation;
import com.swp.g3.entity.Customer;
import com.swp.g3.service.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CompensationController {
    @Autowired
    CompensationService compensationService;
    @GetMapping (value = "/api/customer/compensation/list")
    public List<Compensation> viewAll(HttpSession session){
        return compensationService.findAllByCustomerId(1);
//        Customer customer = (Customer)session.getAttribute("customer");
//        if(customer == null){
//            return null;
//        }else {
//            return compensationService.findAllByCustomerId(customer.getId());
//        }
    }
}
