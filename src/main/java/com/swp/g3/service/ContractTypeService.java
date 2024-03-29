package com.swp.g3.service;

import com.swp.g3.entity.ContractType;
import com.swp.g3.repository.ContractTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractTypeService {

    @Autowired
    ContractTypeRepository contractTypeRepository;

    public List<ContractType> findAll(){
        return contractTypeRepository.findAll();
    }
    public ContractType findOneById(int id){
        return contractTypeRepository.findOneById(id);
    }
    public ContractType save(ContractType contractType){
        return contractTypeRepository.save(contractType);
    }
    public void deleteById(int id){
        contractTypeRepository.deleteById(id);
    }
}
