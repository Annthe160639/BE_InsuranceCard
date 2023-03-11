package com.swp.g3.controller;

import com.swp.g3.entity.Compensation;
import com.swp.g3.entity.Customer;
import com.swp.g3.entity.Staff;
import com.swp.g3.repository.CompensationRepository;
import com.swp.g3.service.CompensationService;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
public class CompensationController {
    @Autowired
    CompensationService compensationService;
    @GetMapping (value = "/api/customer/compensation/list")
    public List<Compensation> viewAll(HttpSession session){
        Customer customer = (Customer)session.getAttribute("customer");
        if(customer == null){
            return null;
        }else {
            return compensationService.findAllByCustomerId(customer.getId());
        }
    }

    @GetMapping("/api/staff/compensation/history")
    public List<Compensation> showCompensationHistory(HttpSession session){
        Staff staff = (Staff)session.getAttribute("staff");
        return compensationService.findAllByStaffId(staff.getId());
    }
    @GetMapping ("/api/staff/compensation/list")
    public List<Compensation> showCompensationList(){
        return compensationService.findAllByStatus("Đang chờ xử lý");
    }
    @GetMapping(value = "/api/staff/compensation/{id}")
    public Compensation showCompensationDetail(@PathVariable int id, HttpSession session){
        Staff staff = (Staff)session.getAttribute("staff");
        Compensation compensation =  compensationService.findOneById(id);
        if(compensation.getStaffId() == null || compensation.getStaffId() == staff.getId()){
            return compensation;
        }
        return null;
    }

}
