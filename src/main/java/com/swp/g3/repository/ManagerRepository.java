package com.swp.g3.repository;

import com.swp.g3.entity.Customer;
import com.swp.g3.entity.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    public Manager findOneByUsername(String username);
    public Manager findOneByUsernameAndPassword(String username, String password);
    public Manager save(Manager manager);
    public List<Manager> findAll();
    public int deleteById(int id);
    public Manager findOneById(int id);
}
