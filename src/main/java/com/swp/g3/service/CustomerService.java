package com.swp.g3.service;

import com.swp.g3.entity.Customer;
import com.swp.g3.entity.CustomerDetails;
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

@Service
public class CustomerService implements UserDetailsService {
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
    public Page<Customer> findCustomers(Pageable pageable){
        Page<Customer> p = customerRepository.findCustomers(pageable);
        return p;
    }
    public Customer findOneByUsernameAndPassword(String username, String password){
        return customerRepository.findOneByUsernameAndPassword(username, password);
    }
    @Transactional
    public CustomerDetails loadUserByUsername(String username){
        Customer c = customerRepository.findOneByUsername(username);
        if(c == null){
            throw new UsernameNotFoundException(username);
        }
        return new CustomerDetails(c);
    }

}

