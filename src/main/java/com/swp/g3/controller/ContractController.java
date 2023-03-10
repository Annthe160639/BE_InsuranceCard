package com.swp.g3.controller;

import com.swp.g3.entity.*;
import com.swp.g3.repository.ContractRepository;
import com.swp.g3.service.BuyerService;
import com.swp.g3.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.HttpConstraint;
import javax.servlet.http.HttpSession;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
public class ContractController {
    @Autowired
    ContractService contractService;
    @Autowired
    BuyerService buyerService;
    @Autowired
    ContractRepository contractRepository;

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
    @GetMapping(value = "/api/staff/contract/list")
    public List<Contract>viewContractList(){
        List<Contract> contractList = contractService.findAllByStatus("??ang ch??? x??? l??");
        return contractList;
    }
    @GetMapping(value = "/api/staff/contract/history")
    public List<Contract>viewContractHistory(HttpSession session){
        Staff staff = (Staff)session.getAttribute("staff");
        int staffId = staff.getId();
        List<Contract> contractList = contractService.findAllByStaffId(staffId);
        return contractList;
    }

    @PutMapping(value = "/api/manager/contract/approve/{id}")
    public Contract approveNewContract(HttpSession session, @PathVariable int id){
        Manager manager = (Manager)session.getAttribute("manager");
        Contract contract = contractService.findOneById(id);
        contract.setManagerId(manager.getId());
        contract.setStatus("???? duy???t");
        contractService.save(contract);
        return contract;
    }
    //detele contract
    @DeleteMapping(value = "/api/customer/contract/view/cancel/{id}")
    public ResponseEntity<?> delete(@PathVariable int id, HttpSession session) {
        Customer customer = (Customer)session.getAttribute("customer");
        int customerId = customer.getId();
        Contract contract = contractService.findOneByIdAndCustomerId(id, customerId);
        if(contract != null){
            contractRepository.deleteById(id);
            return ResponseEntity.ok("H???y y??u c???u h???p ?????ng th??nh c??ng!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("badRequest");
    }
}
