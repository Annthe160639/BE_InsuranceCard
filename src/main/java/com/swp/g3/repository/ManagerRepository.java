package com.swp.g3.repository;

import com.swp.g3.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    public Manager findOneByUsername(String username);
    public Manager findOneByUsernameAndPassword(String username, String password);

}
