package com.swp.g3.service;

import com.swp.g3.entity.Contract;
import com.swp.g3.repository.ContractRepository;
import com.swp.g3.repository.ContractTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    ContractTypeRepository contractTypeRepository;

    public Contract save(Contract newContract) {
        return contractRepository.save(newContract);
    }

    public List<Contract> findAllByCustomerId(int id) {
        List<Contract> contracts = contractRepository.findAllByCustomerId(id);
        for (Contract c : contracts){
            c.setContractType(contractTypeRepository.findOneById(c.getTypeId()));
        }
        return contracts;
    }

    public Contract findOneByIdAndCustomerId(int id, int customerId) {
        return contractRepository.findOneByIdAndCustomerId(id, customerId);
    }

    public List<Contract> findAllByStatus(String status) {
        return contractRepository.findAllByStatus(status);
    }

    public List<Contract> findAllByStaffId(int id) {
        return contractRepository.findAllByStaffId((id));
    }

    public List<Contract> findAllByStaffIdAndStatus(int id, String status) {
        return contractRepository.findAllByStaffIdAndStatus(id, status);
    }

    public Contract findOneById(int id) {
        return contractRepository.findOneById(id);
    }
    public List<Contract> findAll(){
        return contractRepository.findAll();

    }
}
