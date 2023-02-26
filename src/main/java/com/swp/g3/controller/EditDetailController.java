package com.swp.g3.controller;

import com.swp.g3.entity.Customer;
import com.swp.g3.repository.CustomerRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class EditDetailController {
    CustomerRepository customerRepository;


    @RequestMapping(value = "welcome/edit/{username}", method = RequestMethod.GET)
    public String getEditUserData(@PathVariable("username") String username, Model model, Customer accountForm) {

        model.addAttribute("username", username);
        return "editProfile";
    }

    @RequestMapping(value = "editProfile", method = RequestMethod.GET)
    public String showEditProfilePage(Model model, Customer customer) {
        model.addAttribute("username", customer.getUsername());
        model.addAttribute("password", customer.getPassword());
        return "editProfile";
    }

    @RequestMapping(value = "editProfile", method = RequestMethod.POST)
    public String sendEditProfilePage(@ModelAttribute("accountForm") Customer customer) {
        Customer accountInstance = customerRepository.findOneByUsername(customer.getUsername());

        accountInstance.setName(customer.getName());
        accountInstance.setPassword(customer.getGmail());
        accountInstance.setPhone(customer.getPhone());
        accountInstance.setAddress(customer.getAddress());
        accountInstance.setCi(customer.getCi());
        accountInstance.setManagerId(customer.getManagerId());


        customerRepository.save(customer);

        return "editProfile";
    }
}
