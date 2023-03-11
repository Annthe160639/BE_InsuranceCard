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
    public List<Contract> findAll(){
        return contractRepository.findAll();

    }
    public Contract findOneById(int id){
        return contractRepository.findOneById(id);
    }
}
