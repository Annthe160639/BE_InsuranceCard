package com.swp.g3.repository;

import com.swp.g3.entity.Compensation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompensationRepository extends JpaRepository<Compensation, Integer> {
    public List<Compensation> findAll();
    public Compensation findOneById(int id);
}