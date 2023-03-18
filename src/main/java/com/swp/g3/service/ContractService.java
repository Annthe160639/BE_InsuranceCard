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
        for (Contract c : contracts) {
            c.setContractType(contractTypeRepository.findOneById(c.getTypeId()));
        }
        return contracts;
    }

    public List<Contract> findAllByCustomerIdAndStatus(int id, String status) {
        List<Contract> contracts = contractRepository.findAllByCustomerIdAndStatus(id, status);
        for (Contract c : contracts) {
            c.setContractType(contractTypeRepository.findOneById(c.getTypeId()));
        }
        return contracts;
    }

    public Contract findOneByIdAndCustomerId(int id, int customerId) {
        Contract c = contractRepository.findOneByIdAndCustomerId(id, customerId);
        c.setContractType(contractTypeRepository.findOneById(c.getTypeId()));
        return c;
    }

    public List<Contract> findAllByStatus(String status) {
        List<Contract> contracts = contractRepository.findAllByStatus(status);
        for (Contract c : contracts) {
            c.setContractType(contractTypeRepository.findOneById(c.getTypeId()));
        }
        return contracts;
    }

    public List<Contract> findAllByStaffId(int id) {
        List<Contract> contracts = contractRepository.findAllByStaffId(id);
        for (Contract c : contracts) {
            c.setContractType(contractTypeRepository.findOneById(c.getTypeId()));
        }
        return contracts;
    }

    public List<Contract> findAllByStaffIdAndStatus(int id, String status) {
        List<Contract> contracts = contractRepository.findAllByStaffIdAndStatus(id, status);
        for (Contract c : contracts) {
            c.setContractType(contractTypeRepository.findOneById(c.getTypeId()));
        }
        return contracts;
    }

    public Contract findOneByIdAndStaffId(int id, int staffId) {
        Contract c = contractRepository.findOneByIdAndStaffId(id, staffId);
        if (c == null) {
            c = contractRepository.findOneById(id);
            if (c.getStatus() == "Đang chờ xử lý") {
                c.setContractType(contractTypeRepository.findOneById(c.getTypeId()));
                return c;
            }
        } else {
            c.setContractType(contractTypeRepository.findOneById(c.getTypeId()));
        }
        return c;
    }

    public Contract findOneById(int id) {
        Contract c = contractRepository.findOneById(id);
        c.setContractType(contractTypeRepository.findOneById(c.getTypeId()));
        return c;
    }

    public List<Contract> findAll() {
        List<Contract> contracts = contractRepository.findAll();
        for (Contract c : contracts) {
            c.setContractType(contractTypeRepository.findOneById(c.getTypeId()));
        }
        return contracts;

    }

    public List<Contract> findAllByManagerId(int id) {
        return contractRepository.findAllByManagerId(id);
    }
}
