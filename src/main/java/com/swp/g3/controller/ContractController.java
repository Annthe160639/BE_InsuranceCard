package com.swp.g3.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.swp.g3.entity.*;
import com.swp.g3.repository.ContractRepository;
import com.swp.g3.service.BuyerService;
import com.swp.g3.service.ContractService;
import com.swp.g3.service.ContractTypeService;
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
    ContractTypeService contractTypeService;
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

    @GetMapping(value = "/api/customer/contract")
    public List<Contract> loadContractList(HttpServletRequest request) {
        Customer customer = jwtTokenUtil.getCustomerFromRequestToken(request);
        return contractService.findAllByCustomerId(customer.getId());
    }

    @GetMapping(value = "/api/customer/contract/{id}")
    public ResponseEntity<?> viewContractDetails(@PathVariable int id, HttpServletRequest request) {
        Customer customer = jwtTokenUtil.getCustomerFromRequestToken(request);
        int customerId = customer.getId();
        Contract contract = contractService.findOneByIdAndCustomerId(id, customerId);
        if (contract != null) {
            contract.setBuyer(buyerService.findBuyerByid(contract.getBuyerId()));
            contract.setContractType(contractTypeService.findOneById(contract.getTypeId()));
            return ResponseEntity.ok(contract);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

    @GetMapping(value = "/api/staff/contract/list")
    public List<Contract> viewContractList() {
        List<Contract> contractList = contractService.findAllByStatus("Ðang chờ xử lý");
        return contractList;
    }

    @GetMapping(value = "/api/staff/contract")
    public List<Contract> viewContractHistory(HttpServletRequest request) {
        Staff staff = (Staff) jwtTokenUtil.getStaffFromRequestToken(request);
        int staffId = staff.getId();
        List<Contract> contractList = contractService.findAllByStaffId(staffId);
        return contractList;
    }
    @GetMapping(value = "/api/manager/contract")
    public List<Contract> viewContract(HttpServletRequest request) {
        Manager m = (Manager)jwtTokenUtil.getManagerFromRequestToken(request);
        List<Contract> contractList = contractService.findAll();
        return contractList;
    }

    @GetMapping(value = "/api/staff/contract/{id}")
    public ResponseEntity<?> viewContract(HttpServletRequest request, @PathVariable int id) {
        Staff staff = (Staff) jwtTokenUtil.getStaffFromRequestToken(request);
        Contract c = contractService.findOneByIdAndStaffId(id, staff.getId());
        if (c == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
        return ResponseEntity.ok(c);
    }
    @GetMapping(value = "/api/manager/contract/{id}")
    public ResponseEntity<?> viewContractById(HttpServletRequest request, @PathVariable int id) {
        Manager manager = (Manager) jwtTokenUtil.getManagerFromRequestToken(request);
        Contract contract = contractService.findOneById(id);
        if (contract != null) {
            contract.setBuyer(buyerService.findBuyerByid(contract.getBuyerId()));
            contract.setContractType(contractTypeService.findOneById(contract.getTypeId()));
            return ResponseEntity.ok(contract);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hợp đồng không tồn tại");
    }
    @PutMapping(value = "/api/manager/contract/reject/{id}")
    public ResponseEntity<?> rejectNewContract(HttpServletRequest request, @PathVariable int id) {
        Manager manager = (Manager) jwtTokenUtil.getManagerFromRequestToken(request);
        Contract contract = contractService.findOneById(id);
        if (contract.getStatus().equals("Đang xử lý")) {
            contract.setManagerId(manager.getId());
            contract.setStatus("Đã từ chối");
            contractService.save(contract);
            return ResponseEntity.ok(contract);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");

    }
    @PutMapping(value = "/api/manager/contract/approve/{id}")
    public ResponseEntity<?> approveNewContract(HttpServletRequest request, @PathVariable int id) {
        Manager manager = (Manager) jwtTokenUtil.getManagerFromRequestToken(request);
        Contract contract = contractService.findOneById(id);
        if (contract.getStatus().equals("Đang xử lý")) {
            contract.setManagerId(manager.getId());
            contract.setStatus("Đã duyệt");
            contractService.save(contract);
            return ResponseEntity.ok(contract);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");

    }

    //detele contract
    @DeleteMapping(value = "/api/customer/contract/cancel/{id}")
    public ResponseEntity<?> delete(@PathVariable int id, HttpServletRequest request) {
        Customer customer = jwtTokenUtil.getCustomerFromRequestToken(request);
        int customerId = customer.getId();
        Contract contract = contractService.findOneByIdAndCustomerId(id, customerId);
        if (contract != null) {
            contract.setStatus("Đã hủy");
            contractRepository.save(contract);
            return ResponseEntity.ok("Hủy yêu cầu hợp đồng thành công!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("badRequest");
    }

}
