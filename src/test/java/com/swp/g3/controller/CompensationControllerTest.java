package com.swp.g3.controller;

import com.swp.g3.entity.Compensation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompensationController.class)

class CompensationControllerTest {
//    @Autowired
//    private MockMvc mvc;
//    @Test
//    void viewListCompensation() throws Exception{
//        RequestBuilder request = MockMvcRequestBuilders.get("/api/customer/compensation");
//        MvcResult result = mvc.perform(request).andReturn();
//        assertNotNull(result.getResponse().getContentAsString());
//    }
}