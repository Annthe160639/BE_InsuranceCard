package com.swp.g3.controller;

import com.swp.g3.entity.Compensation;
import com.swp.g3.entity.Customer;
import com.swp.g3.entity.Manager;
import com.swp.g3.entity.Staff;
import com.swp.g3.repository.CompensationRepository;
import com.swp.g3.service.CompensationService;
import com.swp.g3.util.JwtTokenUtil;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
public class CompensationController {
    @Autowired
    CompensationService compensationService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @GetMapping (value = "/api/customer/compensation/list")
    public List<Compensation> viewAll(HttpServletRequest request){
        Customer customer = jwtTokenUtil.getCustomerFromRequestToken(request);
        if(customer == null){
            return null;
        }else {
            return compensationService.findAllByCustomerId(customer.getId());
        }
    }

    @GetMapping("/api/staff/compensation")
    public List<Compensation> showCompensationHistory(HttpServletRequest request){
        Staff staff = jwtTokenUtil.getStaffFromRequestToken(request);
        return compensationService.findAllByStaffId(staff.getId());
    }
    @GetMapping ("/api/staff/compensation/list")
    public List<Compensation> showCompensationList(){
        return compensationService.findAllByStatus("Đang chờ xử lý");
    }
    @GetMapping(value = "/api/staff/compensation/{id}")
    public Compensation showCompensationDetail(@PathVariable int id, HttpServletRequest request){
        Staff staff = jwtTokenUtil.getStaffFromRequestToken(request);
        Compensation compensation =  compensationService.findOneById(id);
        if(compensation.getStaffId() == null || compensation.getStaffId() == staff.getId()){
            return compensation;
        }
        return null;
    }
    @GetMapping(value = "/api/manager/compensation/{id}")
    public Compensation showCompensation(@PathVariable int id, HttpServletRequest request){
        Manager manager = jwtTokenUtil.getManagerFromRequestToken(request);
        Compensation compensation =  compensationService.findOneById(id);
        if(compensation.getManagerId() == null || compensation.getManagerId() == manager.getId()){
            return compensation;
        }
        return null;
    }
    @GetMapping("/api/manager/compensation")
    public List<Compensation> showListCompensation(HttpServletRequest request){
        Manager manager = jwtTokenUtil.getManagerFromRequestToken(request);
        if(manager == null){
            return null;
        } else {
            return compensationService.findAllByManagerId(manager.getId());
        }
    }
    @GetMapping ("/api/manager/compensation/list")
    public List<Compensation> showCompensation(){
        return compensationService.findAllByStatus("Đang xử lý");
    }
    @GetMapping(value = "/api/customer/compensation/{id}")
    public ResponseEntity<?> showCompensationById(@PathVariable int id, HttpServletRequest request){
        Customer customer = jwtTokenUtil.getCustomerFromRequestToken(request);
        Compensation compensation =  compensationService.findOneById(id);
        if(customer.getId() != compensation.getCustomerId()){
            return  ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body("");
        }
        return ResponseEntity.ok(compensation);
    }
}
