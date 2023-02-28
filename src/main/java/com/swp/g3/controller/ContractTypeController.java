package com.swp.g3.controller;

import com.swp.g3.entity.ContractType;
import com.swp.g3.service.ContractTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContractTypeController {
    @Autowired
    ContractTypeService contractTypeService;

    @GetMapping(value = "/api/contract/type/list")
    public List<ContractType> showListContractType(){
        return contractTypeService.findAll();
    }
    @GetMapping(value = "/api/contract/type/detail/{id}")
    public ContractType showContractTypeDetail(@PathVariable int id){
        return contractTypeService.findOneById(id);
    }
}
