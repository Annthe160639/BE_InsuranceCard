package com.swp.g3.controller;

import com.swp.g3.entity.Compensation;
import com.swp.g3.repository.CompensationRepository;
import com.swp.g3.service.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ConpensationController {
    @Autowired
    CompensationService compensationService;
    @PostMapping("/api/compensation/list")
    public List<Compensation> showListCompensation(){
        return compensationService.findAll();
    }
    @GetMapping(value = "/api/compensation/detail/{id}")
    public Compensation showCompensationById(@PathVariable int id){
        return compensationService.getOneById(id);
    }


}
