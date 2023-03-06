package com.swp.g3.controller;

import com.swp.g3.entity.ContractType;
import com.swp.g3.entity.Manager;
import com.swp.g3.service.ContractTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @PostMapping(value = "/api/contract/type/add")
    public ContractType addContractType(@RequestBody ContractType contractType, HttpSession session){
        Manager manager = (Manager) session.getAttribute("manager");
        contractType.setManagerId(manager.getId());

        return contractTypeService.save(contractType);
    }
}
