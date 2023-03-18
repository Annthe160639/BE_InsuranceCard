package com.swp.g3.controller;

import com.swp.g3.entity.Customer;
import com.swp.g3.repository.CustomerRepository;
import com.swp.g3.entity.jwt.JwtRequest;
import com.swp.g3.entity.jwt.JwtResponse;
import com.swp.g3.service.EmailService;
import com.swp.g3.service.CustomerService;
import com.swp.g3.service.JwtUserDetailsService;
import com.swp.g3.util.Crypto;
import com.swp.g3.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class CustomerController {
    @Autowired
    Crypto crypto;

    @Autowired
    CustomerService customerService;
    @Autowired
    EmailService emailService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @PostMapping(value = "/api/customer/register")
    public boolean register(@Valid @RequestBody Customer customer) {
        try {
            String encryptedPassword = crypto.encrypt(customer.getPassword());
            customer.setPassword(encryptedPassword);
            customerService.save(customer);
            emailService.sendVerifyEmail(customer.getGmail(), customer.getUsername(), customer.getName());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

// change password
    @PostMapping(value = "/api/customer/password/change")
    public @ResponseBody String changePassword(@RequestParam String oldPassword, @RequestParam String password, @RequestParam String password2, HttpServletRequest request){
        Customer customer = jwtTokenUtil.getCustomerFromRequestToken(request);
        try {
            String encryptedPassword = crypto.encrypt(oldPassword);
            if(encryptedPassword.equals(customer.getPassword())){
                if (password.equals(password2)) {
                    encryptedPassword = crypto.encrypt(password);
                    customer.setPassword(encryptedPassword);
                    customerRepository.save(customer);
                    return "Thay đổi mật khẩu thành công!";
                }else {
                    return "Mật khẩu không khớp!";
                }
            }else {
                return "Sai mật khẩu!";
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "Mã hóa mật khẩu lỗi.";
        }
    }

    @GetMapping(value = "/api/customer/username/duplicate/{username}")
    public boolean checkExistedUsername(@PathVariable String username) {
        return customerService.findOneByUsername(username) != null;
    }

    @GetMapping(value = "/api/customer/gmail/duplicate/{gmail}")
    public boolean checkExistedEmail(@PathVariable String gmail) {
        return customerService.findOneByGmail(gmail) != null;
    }

    public int getNumberTest(int a, int b){
        return a+b;
    }


    @PostMapping(value = "/api/customer/login")
    @ResponseBody
    public ResponseEntity login(@RequestBody JwtRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        String status = "";

        Customer customer = customerService.findOneByUsername(username);
        System.out.println(customer);
        if (customer != null) {
            try {
                String encryptedPassword = crypto.encrypt(password);
                System.out.println(encryptedPassword);
                customer = customerService.findOneByUsernameAndPassword(username, encryptedPassword);

                if (customer == null) {
                    status = "Sai mật khẩu";
                } else {
                    try {
                        authenticate(username, password);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    final String token = jwtTokenUtil.generateToken(customer);
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

    @PostMapping(value = "/api/customer/password/reset")
    public String resetPassword(@RequestParam(name = "username", required = true) String username) {
        Customer c = customerService.findOneByUsername(username);

        if (c == null) return "Người dùng không hợp lệ.";
        else {
            try {
                emailService.sendResetPasswordEmail(c.getGmail(), c.getUsername(), c.getName());
                return "Hãy kiểm tra hòm thư của bạn để đặt lại mật khẩu.";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Người dùng không hợp lệ";
            }
        }
    }

    //for all custommer
//    @GetMapping(value = "/api/customer/getAll")
//    public List<Customer> getAllCustomer() {
//        return customerService.getAllCustomers();
//    }

    @GetMapping("/api/customer/profile")
    public ResponseEntity<?> showCustomerInfo(HttpServletRequest request) {
        Customer customer = jwtTokenUtil.getCustomerFromRequestToken(request);
        if(customer != null){
            customer.setPassword("");
            customer.setManagerId(1);
            return ResponseEntity.ok(customer);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }
    @GetMapping(value = "api/staff/detail/{id}")
    public Customer CustomerDetail(@PathVariable Integer id){
        return customerService.findOneById(id);
    }

    @PutMapping(value = "/api/customer/profile/edit")
    public ResponseEntity<?> editProfile(HttpServletRequest request, @RequestBody Customer customer) {
        Customer c = jwtTokenUtil.getCustomerFromRequestToken(request);
        c.setName(customer.getName());
        c.setPhone(customer.getPhone());
        c.setGmail(customer.getGmail());
        c.setAddress(customer.getAddress());
        c.setCi(customer.getCi());
        customerService.save(c);
        return ResponseEntity.ok(c);
    }
    @GetMapping("/api/staff/customer/{id}")
    private ResponseEntity<?> getCustomerInfo(@PathVariable int id){
        Customer c = customerService.findOneById(id);
        if(c == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        return ResponseEntity.ok(c);
    }

}
