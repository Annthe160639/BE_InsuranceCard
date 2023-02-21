package com.swp.g3.controller;

import com.swp.g3.entity.Customer;
import com.swp.g3.repository.CustomerRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class EditDetailController {
    CustomerRepository customerRepository;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String showWelcmPage(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "welcome/edit/{username}", method = RequestMethod.GET)
    public String getEditUserData(@PathVariable("username") String username, Model model, Customer accountForm) {

        model.addAttribute("accountEmail", username);
        return "editProfile";
    }

    @RequestMapping(value = "editProfile", method = RequestMethod.GET)
    public String showEditProfilePage(Model model, Customer customer) {
        model.addAttribute("username", customer.getUsername());
        model.addAttribute("password", customer.getPassword());
        return "editProfile";
    }

    @RequestMapping(value = "editProfile", method = RequestMethod.POST)
    public String sendEditProfilePage(@ModelAttribute("accountForm") Customer customer, Model model) {
        Customer accountInstance = customerRepository.findOneByUsername(customer.getUsername());

        accountInstance.setUsername(customer.getUsername());
        accountInstance.setPassword(customer.getPassword());

        customerRepository.save(customer);

        return "editProfile";
    }
}
