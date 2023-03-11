package com.swp.g3.service;

import com.swp.g3.entity.Compensation;
import com.swp.g3.repository.CompensationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CompensationService {
    @Autowired
    CompensationRepository compensationRepository;
    public List<Compensation> findAll(){
        return compensationRepository.findAll();
    }
    public Compensation getOneById(int id){
        return compensationRepository.findOneById(id);
    }
}
