package com.swp.g3.repository;

import com.swp.g3.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

}
