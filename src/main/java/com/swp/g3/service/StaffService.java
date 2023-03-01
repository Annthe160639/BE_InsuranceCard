package com.swp.g3.service;

import com.swp.g3.entity.Staff;
import com.swp.g3.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {
    @Autowired
    StaffRepository staffRepository;
    public Staff findOneByUsernameAndPassword(String username, String password){
        return staffRepository.findOneByUsernameAndPassword(username, password);
    }
    public Staff findOneByUsername(String username){
        return staffRepository.findOneByUsername(username);
    }
}
