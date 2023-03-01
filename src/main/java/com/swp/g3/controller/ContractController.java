package com.swp.g3.controller;

import com.swp.g3.entity.Contract;
import com.swp.g3.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContractController {
    @Autowired
    ContractRepository contractRepository;

    //detele contract
    @GetMapping(value = "/api/contract/view/cancel/{id}")
    public String delete(@PathVariable int id) {
        contractRepository.deleteById(id);
        return "cancel successfully";
    }
}
