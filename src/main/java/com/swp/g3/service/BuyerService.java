package com.swp.g3.service;

import com.swp.g3.entity.Buyer;
import com.swp.g3.repository.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerService {
    @Autowired
    BuyerRepository buyerRepository;

    public Buyer save(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    public Buyer findBuyerByid(int id) {
        return buyerRepository.findOneById(id);
    }
}
