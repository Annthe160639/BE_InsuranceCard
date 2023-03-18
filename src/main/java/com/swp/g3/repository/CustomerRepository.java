package com.swp.g3.repository;

import com.swp.g3.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    public Customer findOneByUsernameAndPassword(String username, String password);
    public Customer save(Customer newCustomer);
    public Customer findOneByUsername(String username);
    public Customer findOneByGmail(String gmail);
    public List<Customer> findAll();
    @Query(value = "SELECT * FROM Customer", nativeQuery = true)
    Page<Customer> findCustomers(Pageable pageable);


    public Customer findOneById(Integer id);
}



