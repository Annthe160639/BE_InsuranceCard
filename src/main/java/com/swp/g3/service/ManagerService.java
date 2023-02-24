package com.swp.g3.service;

import com.swp.g3.entity.Manager;
import com.swp.g3.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    @Autowired
    ManagerRepository managerRepository;

    public Manager findOneByUsername(String username){
       return managerRepository.findOneByUsername(username);
    }
    public Manager findOneByUsernameAndPassword(String username, String password){
        return managerRepository.findOneByUsernameAndPassword(username, password);
    }
}
