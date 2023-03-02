package com.swp.g3.controller;

import com.swp.g3.entity.Buyer;
import com.swp.g3.entity.Contract;
import com.swp.g3.entity.Customer;
import com.swp.g3.service.BuyerService;
import com.swp.g3.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ContractController {
    @Autowired
    ContractService contractService;
    @Autowired
    BuyerService buyerService;

    @RequestMapping(value = "/api/customer/contract/request/{id}")
    public Contract save(@RequestBody Contract contract, @PathVariable int id, HttpSession session, @RequestBody Buyer buyer){

        contract.setTypeId(id);
        buyerService.save(buyer);
        contract.setBuyerId(buyer.getId());
        Customer customer = (Customer)session.getAttribute("customer");
        int customerId = customer.getId();
        contract.setCustomerId(customerId);
        return contractService.save(contract);
    }

    @GetMapping(value = "/api/customer/contract/history")
    public List<Contract> loadContractList(HttpSession session){
        Customer customer = (Customer)session.getAttribute("customer");
        int customerId = customer.getId();
        return contractService.findAllByCustomerId(customerId);
    }

    @GetMapping(value = "/api/customer/contract/{id}")
    public Contract viewContractDetail(HttpSession session, @PathVariable int id){
        Customer customer = (Customer)session.getAttribute("customer");
        int customerId = customer.getId();
        Contract contract = contractService.findOneByIdAndCustomerId(id, customerId);
        return contract;
    }
}
