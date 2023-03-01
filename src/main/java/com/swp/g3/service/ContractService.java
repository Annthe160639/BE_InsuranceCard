package com.swp.g3.service;

import com.swp.g3.entity.Contract;
import com.swp.g3.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractService {
    @Autowired
    ContractRepository contractRepository;

    public Contract save(Contract newContract){
        return contractRepository.save(newContract);
    }
}
