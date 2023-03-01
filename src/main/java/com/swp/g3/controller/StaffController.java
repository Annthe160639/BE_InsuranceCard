package com.swp.g3.controller;

import com.swp.g3.entity.Customer;
import com.swp.g3.entity.Manager;
import com.swp.g3.entity.Staff;
import com.swp.g3.service.StaffService;
import com.swp.g3.util.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class StaffController {
    @Autowired
    Crypto crypto;
    @Autowired
    StaffService staffService;

    @PostMapping(value = "/api/staff/login")
    public String login(HttpSession session, @RequestParam String username, @NotNull String password) {
        String status = "";
        Staff staff = staffService.findOneByUsername(username);
        if (staff != null) {
            try {
                String encryptedPassword = crypto.encrypt(password);
                System.out.println(encryptedPassword);
                staff = staffService.findOneByUsernameAndPassword(username, password);
                if (staff == null) {
                    status = "Sai mật khẩu";
                } else {
                    session.setAttribute("staff", staff);
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
}
