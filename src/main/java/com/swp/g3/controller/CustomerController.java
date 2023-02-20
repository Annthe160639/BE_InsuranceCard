package com.swp.g3.controller;

import com.swp.g3.entity.Customer;
import com.swp.g3.entity.CustomerDetails;
import com.swp.g3.jwt.JwtTokenProvider;
import com.swp.g3.service.EmailService;
import com.swp.g3.service.CustomerService;
import com.swp.g3.util.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
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

    @GetMapping(value = "/api/customer/username/duplicate/{username}")
    public boolean checkExistedUsername(@PathVariable String username) {
        return customerService.findOneByUsername(username) != null;
    }

    @GetMapping(value = "/api/customer/gmail/duplicate/{gmail}")
    public boolean checkExistedEmail(@PathVariable String gmail) {
        return customerService.findOneByGmail(gmail) != null;
    }

    @GetMapping(value = "/api/customer/username/list")
    public Page<Customer> listCustomer(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                       @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                       @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {

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
    @PostMapping("/api/login")
    public String authenticateUser(@RequestParam String username, @RequestParam @NotNull String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        System.out.println(password);
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = jwtTokenProvider.generateToken((CustomerDetails) authentication.getPrincipal());
        return jwt;
    }
    @PostMapping(value = "/api/customer/login")
    public String login(@RequestParam String username, @NotNull String password) {
        String status = "";
        Customer c = customerService.findOneByUsername(username);
        if (c != null) {
            if (c.isActive() == false) {
                status = "Tài khoản của bạn chưa được xác thực.";
            } else {
                try {
                    String encryptedPassword = crypto.encrypt(password);
                    c = customerService.findOneByUsernameAndPassword(username, encryptedPassword);
                    if (c == null) {
                        status = "Sai mật khẩu";
                    } else {
                        status = "Đăng nhập thành công.";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Mã hóa mật khẩu lỗi.";
                }
            }
        } else {
            status = "Người dùng không hợp lệ.";
        }
        return status;
    }

    @GetMapping(value = "/api/customer/password/reset")
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


}
