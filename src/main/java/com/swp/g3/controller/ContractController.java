package com.swp.g3.controller;

import com.swp.g3.entity.Contract;
import com.swp.g3.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContractController {
    @Autowired
    ContractService contractService;
    @GetMapping("/api/contract/view")
    public List<Contract> viewContract(){
        return contractService.findAll();
    }
    @GetMapping(value = "/api/contract/view/detail/{id}")
    public Contract viewContractById(@PathVariable int id){
        return contractService.findOneById(id);
    }
}
