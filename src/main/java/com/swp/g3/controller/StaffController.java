package com.swp.g3.controller;

import com.swp.g3.entity.*;
import com.swp.g3.entity.Customer;
import com.swp.g3.service.CompensationService;
import com.swp.g3.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@Validated
public class StaffController {
    //update contract status by staff
    @Autowired
    ContractService contractService;
    @Autowired
    CompensationService compensationService;
    @PutMapping(value = "/api/staff/contract/process/{id}")
    public Contract processNewContract(HttpSession session, @PathVariable int id){
        Staff staff = (Staff) session.getAttribute("staff");
        Contract contract = contractService.findOneById(id);
        contract.setStaffId(staff.getId());
        contract.setStatus("Đang xử lý");
        return contract;
    }

    @PutMapping(value = "/api/staff/compensation/check/{id}")
    public Compensation checkCompensation(@PathVariable int id){
        Compensation compensation = compensationService.getOneById(id);
        compensation.setStatus("Đã kiểm tra");
        return compensation;
    }


}
