package com.swp.g3.controller;

import com.swp.g3.entity.*;
import com.swp.g3.entity.jwt.JwtRequest;
import com.swp.g3.entity.jwt.JwtResponse;
import com.swp.g3.repository.CustomerRepository;
import com.swp.g3.repository.ManagerRepository;
import com.swp.g3.service.CompensationService;
import com.swp.g3.service.CustomerService;
import com.swp.g3.service.ManagerService;
import com.swp.g3.service.StaffService;
import com.swp.g3.util.Crypto;
import com.swp.g3.util.JwtTokenUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    StaffService staffService;
    @GetMapping(value = "/api/manager/customer/list")
    public Page<Customer> listCustomer(HttpServletRequest request,
                                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                       @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                       @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {

        Manager manager = jwtTokenUtil.getManagerFromRequestToken(request);
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

    @GetMapping(value = "/api/manager/staff/list")
    public ResponseEntity<?> listStaff(HttpServletRequest request) {

        Manager manager = jwtTokenUtil.getManagerFromRequestToken(request);
        if (manager == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }

        List<Staff> p = staffService.findAll();
        return ResponseEntity.ok(p);
    }
    @GetMapping(value = "/api/manager/list")
    public ResponseEntity<?> listManager(HttpServletRequest request) {
        Manager manager = jwtTokenUtil.getManagerFromRequestToken(request);
        if(manager == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
        List<Manager> p = managerService.findAll();
        return ResponseEntity.ok(p);
    }

    @PutMapping("/api/manager/customer/edit")
    public ResponseEntity editCustomer(@RequestBody Customer customer, HttpServletRequest request){
        Customer c = customerService.findOneById(customer.getId());
        Manager manager = jwtTokenUtil.getManagerFromRequestToken(request);
        customer.setPassword(c.getPassword());
        customer.setManagerId(manager.getId());
        customerService.save(customer);
        return ResponseEntity.ok(customer);
    }
    @PostMapping(value = "/api/manager/login")
    @ResponseBody
    public ResponseEntity login(@RequestBody JwtRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        String status = "";

        Manager manager = managerService.findOneByUsername(username);
        if (manager != null && manager.isStatus() == true) {
            try {
                String encryptedPassword = crypto.encrypt(password);
                manager = managerService.findOneByUsernameAndPassword(username, encryptedPassword);
                if (manager == null) {
                    status = "Sai mật khẩu";
                } else {
                    try {
                        authenticate(username, password);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    final String token = jwtTokenUtil.generateToken(manager);
                    return ResponseEntity.ok(new JwtResponse(token));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            status = "Người dùng không hợp lệ.";
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(status);
    }
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
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
    @GetMapping("/api/manager/customer/{id}")
    private ResponseEntity<?> getCustomerInfo(@PathVariable int id){
        Customer c = customerService.findOneById(id);
        if(c == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        return ResponseEntity.ok(c);
    }
    @Autowired
    CompensationService compensationService;
    //approve compensation request
    @PutMapping(value = "/api/manager/compensation/approve/{id}")
    public ResponseEntity<?> approveCompensation(@PathVariable int id, HttpServletRequest request) {
        Manager manager = jwtTokenUtil.getManagerFromRequestToken(request);
        Compensation compensation = compensationService.findOneById(id);
        if(compensation == null){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }

        else if(compensation.getStatus().equals("Đang xử lý"))
        {
            compensation.setManagerId(manager.getId());
            compensation.setStatus("Đã duyệt");
            compensationService.save(compensation);
            return ResponseEntity.ok(compensation);
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }


    @PutMapping("/api/manager/edit/{id}")
    public ResponseEntity editManager(HttpServletRequest request, @PathVariable int id){
        Manager m = jwtTokenUtil.getManagerFromRequestToken(request);
        Manager c = managerService.findOneById(id);
        if(c != null){
            c.setStatus(false);
            managerService.save(c);
            Staff s = new Staff(c);
            s.setId(null);
            s.setManagerId(m.getId());
            staffService.save(s);
            return ResponseEntity.ok(s);
        }else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }
    @PutMapping("/api/manager/staff/edit/{id}")
    public ResponseEntity editStaff(HttpServletRequest request, @PathVariable int id){
        Manager m = jwtTokenUtil.getManagerFromRequestToken(request);
        Staff c = staffService.findOneById(id);
        if(c != null){
            c.setStatus(false);
            staffService.save(c);
            Manager s = new Manager(c);
            s.setId(null);
            managerService.save(s);

            return ResponseEntity.ok(s);
        }else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }
    @PutMapping("/api/manager/staff/delete/{id}")
    public ResponseEntity deleteStaff(@PathVariable int id, HttpServletRequest request) {
        Staff s = staffService.findOneById(id);
        if (s != null){
            s.setStatus(false);
            staffService.save(s);
            return ResponseEntity.ok(s);
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

    @PutMapping("/api/manager/delete/{id}")
    public ResponseEntity deleteManager(@PathVariable int id, HttpServletRequest request){
        Manager m = managerService.findOneById(id);
        if(m != null){
            m.setStatus(false);
            managerService.save(m);
            return ResponseEntity.ok(m);
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

}
