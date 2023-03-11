package com.swp.g3.controller;

import com.swp.g3.entity.*;
import com.swp.g3.repository.CustomerRepository;
import com.swp.g3.repository.ManagerRepository;
import com.swp.g3.service.CustomerService;
import com.swp.g3.service.ManagerService;
import com.swp.g3.util.Crypto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class ManagerController {
    @Autowired
    ManagerService managerService;
    @Autowired
    Crypto crypto;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    CustomerService customerService;
    @GetMapping(value = "/api/manager/customer/list")
    public Page<Customer> listCustomer(HttpSession session,
                                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                       @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                       @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {

        Manager manager = (Manager) session.getAttribute("manager");
        if (manager == null) {
            return null;
        }
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("id").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("id").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        Page<Customer> p = customerService.findCustomers(pageable);
        return p;
    }
    @PostMapping(value = "/api/manager/login")
    public String login(HttpSession session, @RequestParam String username, @NotNull String password) {
        String status = "";
        Manager manager = managerService.findOneByUsername(username);
        if (manager != null) {
            try {
                String encryptedPassword = crypto.encrypt(password);
                manager = managerService.findOneByUsernameAndPassword(username, password);
                if (manager == null) {
                    status = "Sai mật khẩu";
                } else {
                    session.setAttribute("manager", manager);
                    status = "Đăng nhập thành công.";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Mã hóa mật khẩu lỗi.";
            }
        } else {
            status = "Người dùng không hợp lệ.";
        }
        return status;
    }
    @GetMapping("login")
    public String Login() {
        return "login";
    }

    @GetMapping("home")
    public String home() {
        return "home";
    }

    @GetMapping("editinfo")
    public String editinfo() {
        return "editinfo";
    }

    @GetMapping("info")
    public String info(HttpSession session, Model model) {
        Object user = session.getAttribute("user");
        model.addAttribute("user", user);
        return "info";
    }
    @PostMapping("check")
    public String Check(@RequestParam("username") String username, 
                        @RequestParam("password") String password, 
                        Model model, 
                        HttpSession session) {
        Manager manager = managerService.findOneByUsername(username);
        if(manager.getPassword().equals(password)){
            session.setAttribute("user", manager);
            model.addAttribute("user", session.getAttribute("user"));
            return "home";
        }else {
            return "redirect:/login";
        }
    }
    @PostMapping("checkpasstoeditinfo")
    public String checkpasstoeditinfo(@RequestParam("cfmpass") String pass, 
                                      @RequestParam("username") String username,
                                      @RequestParam("password") String password, 
                                      HttpSession session, 
                                      Model model) {
        Manager user = (Manager) session.getAttribute("user");
        if (user.getPassword().equals(pass)) {
            user.setUsername(username);
            user.setPassword(password);
            Manager updatedUser = managerService.save(user);
            ResponseEntity.ok(updatedUser);
        }
        Object user1 = session.getAttribute("user");
        model.addAttribute("user", user);
        return "info";
    }
}
