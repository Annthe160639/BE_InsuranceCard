package com.swp.g3.controller;

import com.swp.g3.entity.*;
import com.swp.g3.entity.Customer;
import com.swp.g3.entity.Manager;
import com.swp.g3.entity.Staff;
import com.swp.g3.entity.jwt.JwtRequest;
import com.swp.g3.entity.jwt.JwtResponse;
import com.swp.g3.service.StaffService;
import com.swp.g3.util.Crypto;
import com.swp.g3.service.CompensationService;
import com.swp.g3.service.ContractService;
import com.swp.g3.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class StaffController {
    @Autowired
    Crypto crypto;
    @Autowired
    StaffService staffService;
    //update contract status by staff
    @Autowired
    ContractService contractService;
    @Autowired
    CompensationService compensationService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    AuthenticationManager authenticationManager;
    @PutMapping(value = "/api/staff/contract/accept/{id}")
    public ResponseEntity<?> processNewContract(HttpServletRequest request, @PathVariable int id){
        Staff staff = jwtTokenUtil.getStaffFromRequestToken(request);
        Contract contract = contractService.findOneById(id);
        if(contract == null){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
        else if((contract.getStaffId() == null || contract.getStaffId() == staff.getId()) && !contract.getStatus().equals("Đã duyệt"))
        {
            contract.setStaffId(staff.getId());
            contract.setStatus("Đang xử lý");
            contractService.save(contract);
            return ResponseEntity.ok(contract);
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");

    }
    @PutMapping(value = "/api/staff/contract/reject/{id}")
    public ResponseEntity<?> rejectNewContract(HttpServletRequest request, @PathVariable int id){
        Staff staff = jwtTokenUtil.getStaffFromRequestToken(request);
        Contract contract = contractService.findOneById(id);

        if(contract == null){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
        else if((contract.getStaffId() == null || contract.getStaffId() == staff.getId()) && !contract.getStatus().equals("Đã duyệt"))
        {
            contract.setStaffId(staff.getId());
            contract.setStatus("Đã từ chối");
            contractService.save(contract);
            return ResponseEntity.ok(contract);
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }
    @PutMapping(value = "/api/staff/compensation/reject/{id}")
    public ResponseEntity<?> rejectCompensation(HttpServletRequest request, @PathVariable int id){
        Staff staff = jwtTokenUtil.getStaffFromRequestToken(request);
        Compensation compensation = compensationService.findOneById(id);

        if(compensation == null){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
        else if((compensation.getStaffId() == null || compensation.getStaffId() == staff.getId()) && !compensation.getStatus().equals("Đã duyệt"))
        {
            compensation.setStaffId(staff.getId());
            compensation.setStatus("Đã từ chối");
            compensationService.save(compensation);
            return ResponseEntity.ok(compensation);
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }
    @PutMapping(value = "/api/staff/compensation/accept/{id}")
    public ResponseEntity<?> acceptCompensation(HttpServletRequest request, @PathVariable int id){
        Staff staff = jwtTokenUtil.getStaffFromRequestToken(request);
        Compensation compensation = compensationService.findOneById(id);

        if(compensation == null){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
        else if((compensation.getStaffId() == null || compensation.getStaffId() == staff.getId()) && !compensation.getStatus().equals("Đã duyệt"))
        {
            compensation.setStaffId(staff.getId());
            compensation.setStatus("Đã duyệt");
            compensationService.save(compensation);
            return ResponseEntity.ok(compensation);
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }

    @PostMapping(value = "/api/staff/login")
    @ResponseBody
    public ResponseEntity login(@RequestBody JwtRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        String status = "";

        Staff staff = staffService.findOneByUsername(username);
        if (staff != null) {
            try {
                String encryptedPassword = crypto.encrypt(password);
                staff = staffService.findOneByUsernameAndPassword(username, encryptedPassword);
                if (staff == null) {
                    status = "Sai mật khẩu";
                } else {
                    try {
                        authenticate(username, password);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    final String token = jwtTokenUtil.generateToken(staff);
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

}
