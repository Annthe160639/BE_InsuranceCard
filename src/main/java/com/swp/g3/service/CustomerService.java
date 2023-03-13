package com.swp.g3.service;

import com.swp.g3.entity.Customer;
import com.swp.g3.repository.CustomerRepository;
import com.swp.g3.repository.ManagerRepository;
import com.swp.g3.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService  {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    StaffRepository staffRepository;

    public boolean save(Customer newCustomer){
        try{
            customerRepository.save(newCustomer);//insert | save
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
        Customer c = customerRepository.findOneByGmail(gmail);
        return c;
    }
    public Page<Customer> findCustomers(Pageable pageable){
        Page<Customer> p = customerRepository.findCustomers(pageable);
        return p;
    }
    public Customer findOneByUsernameAndPassword(String username, String password){
        return customerRepository.findOneByUsernameAndPassword(username, password);
    }
    public Customer findOneById(Integer id){
        return customerRepository.findOneById(id);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }



}

