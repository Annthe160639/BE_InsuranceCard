package com.swp.g3.controller;

import com.swp.g3.entity.ContractType;
import com.swp.g3.entity.Manager;
import com.swp.g3.entity.Staff;
import com.swp.g3.repository.ContractRepository;
import com.swp.g3.repository.ContractTypeRepository;
import com.swp.g3.service.ContractTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class ContractTypeController {
    @Autowired
    ContractTypeService contractTypeService;


    @GetMapping(value = "/api/contract/type/list")
    public List<ContractType> showListContractType(){
        return contractTypeService.findAll();
    }
    @GetMapping(value = "/api/contract/type/{id}")
    public ContractType showContractTypeDetail(@PathVariable int id) {
        return contractTypeService.findOneById(id);
    }
    @PostMapping ("/api/manager/contract/type/edit")
    public ContractType editContractType (@RequestBody ContractType contractType, HttpSession session) {
        Manager manager = (Manager) session.getAttribute("manager");
        ContractType contrType = contractTypeService.findOneById(contractType.getId());
        contrType.setName(contractType.getName());
        contrType.setVehicleType(contractType.getVehicleType());
        contrType.setPrice(contractType.getPrice());
        contrType.setInsuranceLevel(contractType.getInsuranceLevel());
        contrType.setDescription(contractType.getDescription());
        contrType.setManagerId(manager.getId());
        contractTypeService.save(contrType);
        return contrType;
    }
    @PostMapping(value = "/api/manager/contract/type/add")
    public ContractType addContractType(@RequestBody ContractType contractType, HttpSession session){
        Manager manager = (Manager) session.getAttribute("manager");
        contractType.setManagerId(manager.getId());

        return contractTypeService.save(contractType);
    }
}
