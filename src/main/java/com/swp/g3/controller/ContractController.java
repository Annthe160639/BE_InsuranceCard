package com.swp.g3.controller;

import com.swp.g3.entity.Contract;
import com.swp.g3.entity.Customer;
import com.swp.g3.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class ContractController {
    @Autowired
    ContractService contractService;

    @RequestMapping(value = "/api/customer/contract/request/{id}")
    public Contract save(@RequestBody Contract contract,@PathVariable int id, HttpSession session){
        contract.setTypeId(id);
        Customer customer = (Customer)session.getAttribute("customer");
        int customerId = customer.getId();
        contract.setCustomerId(customerId);
        return contractService.save(contract);
    }

}
