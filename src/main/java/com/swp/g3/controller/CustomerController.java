package com.swp.g3.controller;

import com.swp.g3.entity.Customer;
import com.swp.g3.entity.CustomerDetails;
import com.swp.g3.jwt.JwtTokenProvider;
import com.swp.g3.repository.CustomerRepository;
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
    CustomerRepository customerRepository;
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




    @PostMapping(value = "/api/customer/login")
    public String login(HttpSession session,
                        @RequestBody(required = false) Customer customer){
        String username = customer.getUsername();
        String password = customer.getPassword();
        String status = "";
        Customer c = customerService.findOneByUsername(username);
        if (c != null) {
            if (c.isActive() == false) {
                status = "T??i kho???n c???a b???n ch??a ???????c x??c th???c.";
            } else {
                try {
                    String encryptedPassword = crypto.encrypt(password);
                    c = customerService.findOneByUsernameAndPassword(username, encryptedPassword);
                    if (c == null) {
                        status = "Sai m???t kh???u";
                    } else {
                        session.setAttribute("customer", c);
                        status = "????ng nh???p th??nh c??ng.";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return "M?? h??a m???t kh???u l???i.";
                }
            }
        } else {
            status = "Ng?????i d??ng kh??ng h???p l???.";
        }
        return status;
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
                    return "Thay ?????i m???t kh???u th??nh c??ng!";
                }else {
                    return "M???t kh???u kh??ng kh???p!";
                }
            }else {
                return "Sai m???t kh???u!";
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "M?? h??a m???t kh???u l???i.";
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



    @GetMapping(value = "/api/customer/password/reset")
    public String resetPassword(@RequestParam(name = "username", required = true) String username) {
        Customer c = customerService.findOneByUsername(username);

        if (c == null) return "Ng?????i d??ng kh??ng h???p l???.";
        else {
            try {
                emailService.sendResetPasswordEmail(c.getGmail(), c.getUsername(), c.getName());
                return "H??y ki???m tra h??m th?? c???a b???n ????? ?????t l???i m???t kh???u.";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Ng?????i d??ng kh??ng h???p l???";
            }
        }
    }


}
