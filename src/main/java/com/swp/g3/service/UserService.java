package com.swp.g3.service;

import com.swp.g3.entity.Customer;
import com.swp.g3.repository.CustomerRepository;
import com.swp.g3.repository.ManagerRepository;
import com.swp.g3.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService  {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    StaffRepository staffRepository;

    public boolean save(Customer newCustomer){
        try{
            customerRepository.save(newCustomer);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public Customer findOneByUsername(String username){
        Customer c = customerRepository.findOneByUsername(username);
        return c;
    }
    public Customer findOneByGmail(String gmail){
        Customer c = customerRepository.findOneByGmail(gmail);
        return c;
    }

}

