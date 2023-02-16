package com.swp.g3.controller;

import com.swp.g3.entity.Customer;
import com.swp.g3.entity.CustomerDetails;
import com.swp.g3.jwt.JwtTokenProvider;
import com.swp.g3.repository.CustomerRepository;
import com.swp.g3.entity.jwt.JwtRequest;
import com.swp.g3.entity.jwt.JwtResponse;
import com.swp.g3.service.EmailService;
import com.swp.g3.service.CustomerService;
import com.swp.g3.service.JwtUserDetailsService;
import com.swp.g3.util.Crypto;
import com.swp.g3.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

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
    private AuthenticationManager authenticationManager;

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
    public @ResponseBody String changepassword(@RequestParam String oldPassword, @RequestParam String password, @RequestParam String password2, HttpSession session){
        Customer customer = (Customer) session.getAttribute("customer");
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


    @PostMapping(value = "/api/customer/login")
    @ResponseBody
    public ResponseEntity login(@RequestBody JwtRequest authenticationRequest) {
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        final Customer customer = customerService.findOneByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(customer);

        return ResponseEntity.ok(new JwtResponse(token));
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
