package com.swp.g3.service;

import com.swp.g3.entity.Compensation;
import com.swp.g3.repository.CompensationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompensationService {
    @Autowired
    CompensationRepository compensationRepository;
    public List<Compensation> findAllByCustomerId(int id){
        return compensationRepository.findAllByCustomerId(id);
    }
    public List<Compensation> findAllByStatus(String status){
        return compensationRepository.findAllByStatus(status);
    }
    public List<Compensation> findAllByStaffId(int id){
        return compensationRepository.findAllByStaffId(id);
    }
    public Compensation findOneById(int id){
        return compensationRepository.findOneById(id);
    }
}
