package com.swp.g3.controller;

import com.swp.g3.entity.Compensation;
import com.swp.g3.entity.Customer;
import com.swp.g3.entity.Manager;
import com.swp.g3.entity.Staff;
import com.swp.g3.service.CompensationService;
import com.swp.g3.service.CustomerService;
import com.swp.g3.service.ManagerService;
import com.swp.g3.util.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class ManagerController {
    @Autowired
    ManagerService managerService;
    @Autowired
    Crypto crypto;
    @Autowired
    CustomerService customerService;
    @Autowired
    CompensationService compensationService;

//    @PostMapping(value = "/api/manager/login")
//    public String login(HttpSession session, @RequestParam String username, @NotNull String password) {
//        String status = "";
//        Manager manager = managerService.findOneByUsername(username);
//        if (manager != null) {
//            try {
//                String encryptedPassword = crypto.encrypt(password);
//                System.out.println(encryptedPassword);
//                manager = managerService.findOneByUsernameAndPassword(username, password);
//                if (manager == null) {
//                    status = "Sai mật khẩu";
//                } else {
//                    session.setAttribute("manager", manager);
//                    status = "Đăng nhập thành công.";
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                return "Mã hóa mật khẩu lỗi.";
//            }
//        } else {
//            status = "Người dùng không hợp lệ.";
//        }
//        return status;
//    }
//
//    @GetMapping(value = "/api/manager/customer/list")
//    public Page<Customer> listCustomer(HttpSession session,
//                                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
//                                       @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
//                                       @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
//
//        Manager manager = (Manager) session.getAttribute("manager");
//        if (manager == null) {
//            return null;
//        }
//        Sort sortable = null;
//        if (sort.equals("ASC")) {
//            sortable = Sort.by("id").ascending();
//        }
//        if (sort.equals("DESC")) {
//            sortable = Sort.by("id").descending();
//        }
//        Pageable pageable = PageRequest.of(page, size, sortable);
//        Page<Customer> p = customerService.findCustomers(pageable);
//        return p;
//    }

    //approve compensation request
    @PutMapping(value = "/api/manager/compensation/approve/{id}")
    public Compensation approveCompensation(@PathVariable int id) {

        Compensation compensation = compensationService.getOneById(id);
        compensation.setStatus("Đã duyệt");
        return compensation;
    }


}
