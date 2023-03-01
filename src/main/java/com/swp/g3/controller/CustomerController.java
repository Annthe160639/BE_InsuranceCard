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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
            System.out.println(customer);
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


    @PostMapping(value = "/api/customer/login")
    @ResponseBody
    public String login(HttpSession session,
                        @RequestBody(required = false) Customer customer,
                        HttpServletResponse response){
        String username = customer.getUsername();
        String password = customer.getPassword();
        Customer c = customerService.findOneByUsername(username);
        if (c != null) {
            if (c.isActive() == false) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                try {
                    String encryptedPassword = crypto.encrypt(password);
                    c = customerService.findOneByUsernameAndPassword(username, encryptedPassword);
                    if (c == null) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    } else {
                        session.setAttribute("customer", c);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        }
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return "aaa";
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
