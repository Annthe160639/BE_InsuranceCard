package com.swp.g3.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.swp.g3.entity.*;
import com.swp.g3.repository.ContractRepository;
import com.swp.g3.service.BuyerService;
import com.swp.g3.service.ContractService;
import com.swp.g3.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.HttpConstraint;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ContractController {
    @Autowired
    ContractService contractService;
    @Autowired
    BuyerService buyerService;
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/api/customer/contract/request/{id}")
    public ResponseEntity<?> save(@RequestBody ObjectNode json, @PathVariable int id, HttpSession session, HttpServletRequest request) {
        try {
            Contract contract = new ObjectMapper().treeToValue(json, Contract.class);
            contract.setTypeId(id);
            Buyer buyer = buyerService.save(contract.getBuyer());
            contract.setBuyerId(buyer.getId());
            Customer customer = jwtTokenUtil.getCustomerFromRequestToken(request);
            int customerId = customer.getId();
            contract.setCustomerId(customerId);
            System.out.println(contract);
            contractService.save(contract);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/api/customer/contract/history")
    public List<Contract> loadContractList(HttpServletRequest request) {
        Customer customer = jwtTokenUtil.getCustomerFromRequestToken(request);
        return contractService.findAllByCustomerId(customer.getId());
    }

    @GetMapping(value = "/api/customer/contract/{id}")
    public Contract viewContractDetail(HttpSession session, @PathVariable int id) {
        Customer customer = (Customer) session.getAttribute("customer");
        int customerId = customer.getId();
        Contract contract = contractService.findOneByIdAndCustomerId(id, customerId);
        return contract;
    }

    @GetMapping(value = "/api/staff/contract/list")
    public List<Contract> viewContractList() {
        List<Contract> contractList = contractService.findAllByStatus("Ðang chờ xử lý");
        return contractList;
    }

    @GetMapping(value = "/api/staff/contract/history")
    public List<Contract> viewContractHistory(HttpSession session) {
        Staff staff = (Staff) session.getAttribute("staff");
        int staffId = staff.getId();
        List<Contract> contractList = contractService.findAllByStaffId(staffId);
        return contractList;
    }

    @PutMapping(value = "/api/manager/contract/approve/{id}")
    public Contract approveNewContract(HttpSession session, @PathVariable int id) {
        Manager manager = (Manager) session.getAttribute("manager");
        Contract contract = contractService.findOneById(id);
        contract.setManagerId(manager.getId());
        contract.setStatus("Đã duyệt");
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
            return ResponseEntity.ok("Hủy yêu cầu hợp đồng thành công!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("badRequest");
    }
}
