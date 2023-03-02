package com.swp.g3.repository;

import com.swp.g3.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    public Contract save(Contract newContract);
    public List<Contract> findAllByCustomerId(int id);
    public Contract findOneByIdAndCustomerId(int id, int customerId);
}
