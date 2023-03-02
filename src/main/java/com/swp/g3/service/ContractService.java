package com.swp.g3.service;

import com.swp.g3.entity.Contract;
import com.swp.g3.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {
    @Autowired
    ContractRepository contractRepository;

    public Contract save(Contract newContract){
        return contractRepository.save(newContract);
    }
    public List<Contract> findAllByCustomerId(int id){return contractRepository.findAllByCustomerId(id);}
    public Contract findOneByIdAndCustomerId(int id, int customerId){
        return contractRepository.findOneByIdAndCustomerId(id, customerId);
    }
}
