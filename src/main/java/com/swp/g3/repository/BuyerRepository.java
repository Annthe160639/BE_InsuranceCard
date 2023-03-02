package com.swp.g3.repository;

import com.swp.g3.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Integer> {
    public Buyer save(Buyer buyer);
}
