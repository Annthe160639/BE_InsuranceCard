package com.swp.g3.controller;

import com.swp.g3.entity.Customer;
import com.swp.g3.service.CustomerService;
import com.swp.g3.util.Crypto;
import com.swp.g3.entity.jwt.*;
import com.swp.g3.util.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
@ContextConfiguration({"classpath*:spring/applicationContext.xml"})
class CustomerControllerTest {
    @Mock
    private Crypto crypto;
    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @Mock
    private HttpServletRequest request;
    @Mock
    private CustomerController customerController;
    @Mock
    private CustomerService customerService;
    @Autowired
    private MockMvc mvc;

//    @Test
//    public void testChangePasswordSuccess() throws Exception{
//        Customer customer = new Customer();
//        customer = customerController.customerService.findOneById(2);
//        customer.setPassword(crypto.encrypt("1234"));
//
//
//
//    }
    @Test
    public void testLoginWithValidCredentials() throws Exception {
        // Arrange
        String username = "cuongc";
        String password = "1234";
        String encryptedPassword = crypto.encrypt(password);
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(encryptedPassword);
        JwtRequest request = new JwtRequest(username, password);
        when(customerService.findOneByUsername(username)).thenReturn(customer);
        when(crypto.encrypt(password)).thenReturn(encryptedPassword);
        when(customerService.findOneByUsernameAndPassword(username, encryptedPassword)).thenReturn(customer);
        when(jwtTokenUtil.generateToken(customer)).thenReturn("token");

        // Act
        ResponseEntity response = customerController.login(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JwtResponse responseBody = (JwtResponse) response.getBody();
        assertEquals("token", responseBody.getToken());
    }

    @Test
    public void testSum() throws Exception{
        int sum = customerController.getNumberTest(3, 4);
        assertEquals(7, sum);
    }






}