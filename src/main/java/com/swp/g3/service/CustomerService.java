package com.swp.g3.service;

import com.swp.g3.entity.Customer;
import com.swp.g3.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;


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
        return customerRepository.findOneByUsername(username);
    }
    public Customer findOneByGmail(String gmail){
        return customerRepository.findOneByGmail(gmail);
    }

    public Customer findOneById(Integer id){
        return customerRepository.findOneById(id);
    }

}

