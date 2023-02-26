package com.swp.g3.controller;

import com.swp.g3.entity.Compensation;
import com.swp.g3.entity.Customer;
import com.swp.g3.repository.CompensationRepository;
import com.swp.g3.repository.CustomerRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class CompensationController {

    CompensationRepository compensationRepository;
    @RequestMapping(value = "/requestCompensation", method = RequestMethod.POST)
    public String sendRequestCompensationPage(@RequestBody Compensation compensation) {

        compensationRepository.save(compensation);

        return "requestCompensation";
    }
}
