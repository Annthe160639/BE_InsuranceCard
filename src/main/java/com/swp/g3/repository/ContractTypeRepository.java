package com.swp.g3.repository;

import com.swp.g3.entity.ContractType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractTypeRepository extends JpaRepository<ContractType, Integer> {
    public List<ContractType> findAll();

    public ContractType findOneById(int id);

    public ContractType save(ContractType contractType);

}
