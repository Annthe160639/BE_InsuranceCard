package com.swp.g3.repository;

import com.swp.g3.entity.Compensation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompensationRepository extends JpaRepository<Compensation, Integer> {
    public List<Compensation> findAllByCustomerId(int id);

    public List<Compensation> findAllByStatus(String status);

    public List<Compensation> findAllByStaffId(int id);
    public List<Compensation> findAllByManagerId(int id);

    public Compensation findOneById(int id);
    public List<Compensation> findAll();
    public Compensation save(Compensation compensation);
}
