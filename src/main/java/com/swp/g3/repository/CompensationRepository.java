package com.swp.g3.repository;

import com.swp.g3.entity.Compensation;
import com.swp.g3.entity.ContractType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompensationRepository extends JpaRepository<Compensation, Integer> {
    public Compensation save(Compensation newCompensation);


}
