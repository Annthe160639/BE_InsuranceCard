package com.swp.g3.controller;

import com.swp.g3.entity.ContractType;
import com.swp.g3.service.ContractTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContractTypeController {
    @Autowired
    ContractTypeService contractTypeService;

    @GetMapping(value = "/api/contracttype/list")
    public List<ContractType> showListContractType(){
        return contractTypeService.findAll();
    }
    @GetMapping(value = "/api/contracttype/detail/{id}")
    public ContractType showContractTypeDetail(@PathVariable int id){
        return contractTypeService.findOneById(id);
    }
}
