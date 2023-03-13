package com.swp.g3.controller;

import com.swp.g3.entity.ContractType;
import com.swp.g3.entity.Manager;
import com.swp.g3.entity.Staff;
import com.swp.g3.repository.ContractRepository;
import com.swp.g3.repository.ContractTypeRepository;
import com.swp.g3.service.ContractTypeService;
import com.swp.g3.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @PostMapping ("/api/manager/contract/type/edit")
    public ContractType editContractType (@RequestBody ContractType contractType, HttpServletRequest request) {
        Manager manager = jwtTokenUtil.getManagerFromRequestToken(request);
        contractType.setManagerId(manager.getId());
        contractTypeService.save(contractType);
        return contractType;
    }
    @PostMapping(value = "/api/manager/contract/type/add")
    public ContractType addContractType(@RequestBody ContractType contractType, HttpSession session){
        Manager manager = (Manager) session.getAttribute("manager");
        contractType.setManagerId(manager.getId());
        return contractTypeService.save(contractType);
    }
    @PostMapping(value = "/api/manager/contract/type/delete/{id}")
    public ResponseEntity<?> deleteContractType(@PathVariable int id){
        if(contractTypeService.findOneById(id) == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }else {
            contractTypeService.deleteById(id);
            return ResponseEntity.ok().body("");
        }
    }
}
