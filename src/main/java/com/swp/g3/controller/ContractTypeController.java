package com.swp.g3.controller;

import com.swp.g3.entity.ContractType;
import com.swp.g3.entity.Manager;
import com.swp.g3.repository.ContractRepository;
import com.swp.g3.repository.ContractTypeRepository;
import com.swp.g3.service.ContractTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ContractTypeController {
    @Autowired
    ContractTypeService contractTypeService;
    @Autowired
    ContractTypeRepository contractTypeRepository;

    @GetMapping(value = "/api/contract/type/list")
    public List<ContractType> showListContractType(){
        return contractTypeService.findAll();
    }
    @GetMapping(value = "/api/contract/type/detail/{id}")
    public ContractType showContractTypeDetail(@PathVariable int id){
        return contractTypeService.findOneById(id);
    @PostMapping("/api/contract/type/edit")
    public String editcontracttype(@ModelAttribute("editcontracttypeForm") ContractType contractType) {
       ContractType contrType = contractTypeRepository.findOneById(contractType.getId());
       contrType.setName(contractType.getName());
        contrType.setVehicleType(contractType.getVehicleType());
        contrType.setPrice(contractType.getPrice());
        contrType.setInsuranceLevel(contractType.getInsuranceLevel());
        contrType.setDescription(contractType.getDescription());
        contrType.setManagerId(contractType.getManagerId());
        contractTypeRepository.save(contrType);
        return "Update Successfully";
    }
//    @RequestMapping(value = "/api/contract/type/list")
//    public List<ContractType> showListContractType(){
//        return contractTypeService.findAll();
//    }
//    @GetMapping(value = "/api/contract/type/detail/{id}")
//    public ContractType showContractTypeDetail(@PathVariable int id){
//        return contractTypeService.findOneById(id);
//    }

    // edit contract type

    @PostMapping(value = "/api/contract/type/add")
    public ContractType addContractType(@RequestBody ContractType contractType, HttpSession session){
        Manager manager = (Manager) session.getAttribute("manager");
        contractType.setManagerId(manager.getId());

        return contractTypeService.save(contractType);
    }
}
