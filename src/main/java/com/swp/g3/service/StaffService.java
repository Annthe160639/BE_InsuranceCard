package com.swp.g3.service;

import com.swp.g3.entity.Customer;
import com.swp.g3.entity.Staff;
import com.swp.g3.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Staff> findAll(){
        List<Staff> p = staffRepository.findAll();
        return p;
    }
    public Staff save(Staff staff){
        return staffRepository.save(staff);
    }
    public int deleteById(int id){
        return staffRepository.deleteById(id);
    }
    public Staff findOneById(int id){
        return staffRepository.findOneById(id);
    }
}
