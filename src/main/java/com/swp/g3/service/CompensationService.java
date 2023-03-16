package com.swp.g3.service;

import com.swp.g3.entity.Compensation;
import com.swp.g3.repository.CompensationRepository;
import com.swp.g3.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompensationService {
    @Autowired
    CompensationRepository compensationRepository;
    @Autowired
    ContractService contractService;

    public Compensation save(Compensation compensation) {
        return compensationRepository.save(compensation);
    }

    public List<Compensation> findAllByCustomerId(int id) {
        List<Compensation> compensations = compensationRepository.findAllByCustomerId(id);
        for (Compensation c : compensations) {
            c.setContract(contractService.findOneById(c.getContractId()));
        }
        return compensations;
    }


    public List<Compensation> findAllByStatus(String status) {
        List<Compensation> compensations = compensationRepository.findAllByStatus(status);
        for (Compensation c : compensations) {
            c.setContract(contractService.findOneById(c.getContractId()));
        }
        return compensations;
    }

    public List<Compensation> findAllByStaffId(int id) {
        List<Compensation> compensations = compensationRepository.findAllByStaffId(id);
        for (Compensation c : compensations) {
            c.setContract(contractService.findOneById(c.getContractId()));
        }
        return compensations;
    }

    public Compensation findOneById(int id) {
        return compensationRepository.findOneById(id);
    }

    public List<Compensation> findAll() {
        return compensationRepository.findAll();
    }

    public List<Compensation> findAllByManagerId(int id) {
        List<Compensation> compensations = compensationRepository.findAllByManagerId(id);
        for (Compensation c : compensations) {
            c.setContract(contractService.findOneById(c.getContractId()));
        }
        return compensations;
    }

    public Compensation findOneByIdAndStaffId(int id, int staffId) {
        return compensationRepository.findOneByIdAndStaffId(id, staffId);
    }
}
