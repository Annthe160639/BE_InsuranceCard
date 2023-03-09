package com.swp.g3.controller;

import com.swp.g3.entity.*;
import com.swp.g3.service.BuyerService;
import com.swp.g3.service.ContractService;
import com.swp.g3.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

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
    JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/api/customer/contract/request/{id}")
    public Contract save(@RequestBody Contract contract, @PathVariable int id, HttpSession session, @RequestBody Buyer buyer, HttpServletRequest request) {

        contract.setTypeId(id);
        buyerService.save(buyer);
        contract.setBuyerId(buyer.getId());
        Customer customer = jwtTokenUtil.getCustomerFromRequestToken(request);
        int customerId = customer.getId();
        contract.setCustomerId(customerId);
        return contractService.save(contract);
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
}
