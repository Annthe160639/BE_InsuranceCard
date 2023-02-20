package com.swp.g3.controller;

import com.swp.g3.entity.Manager;
import com.swp.g3.repository.ManagerRepository;
import com.swp.g3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ManagerController {
    @Autowired
    ManagerRepository repository;
//    @GetMapping("/api/manager/all")
//    public List<Manager> GetAllManager(){
//       return repository.findAll();
//    }

        @GetMapping("/example")
    public String helloWorld(){
        return "<h2>hello may<h2>";
    }
@GetMapping("/Manager/Info/{id}")
public Optional<Manager> findById(@PathVariable int id) {
    Optional<Manager> foundManager = repository.findById(id);
    return foundManager.isPresent() ?
            foundManager : null;
}
}
