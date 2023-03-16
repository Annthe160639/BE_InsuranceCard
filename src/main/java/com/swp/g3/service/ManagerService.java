package com.swp.g3.service;

import com.swp.g3.entity.Manager;
import com.swp.g3.entity.Staff;
import com.swp.g3.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Manager save(Manager manager){
        return managerRepository.save(manager);
    }
    public List<Manager> findAllByStatus(boolean status){
        List<Manager> p = managerRepository.findAllByStatus(status);
        return p;
    }
    public int deleteById(int id){
        return managerRepository.deleteById(id);
    }
    public Manager findOneById(int id){
        return managerRepository.findOneById(id);
    }
}
