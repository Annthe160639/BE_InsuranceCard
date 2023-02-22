package com.swp.g3.controller;


import com.swp.g3.entity.Manager;
import com.swp.g3.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller

public class ManagerController {
    @Autowired
    private ManagerRepository repository;
//    @GetMapping("Info/{id}")
//    public String findById(@PathVariable int id, Model model) {
//       Optional<Manager> user = repository.findById(id);
//       model.addAttribute("user", user.get());
//       return "home";
//     }
    @GetMapping("login")
    public String Login() {
        return "login";
    }

    @GetMapping("home")
    public String home() {
        return "home";
    }

    @GetMapping("editinfo")
    public String editinfo() {
        return "editinfo";
    }

    @GetMapping("info")
    public String info(HttpSession session, Model model) {
        Object user = session.getAttribute("user");
        model.addAttribute("user", user);
        return "info";
    }
    @PostMapping("check")
    public String Check(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session) {
        Manager manager = repository.findByUsername(username);
        if(manager.getPassword().equals(password)){
            session.setAttribute("user", manager);
            model.addAttribute("user", session.getAttribute("user"));
            return "home";
        }else {
            return "redirect:/login";
        }
    }
    @PostMapping("checkpasstoeditinfo")
    public String checkpasstoeditinfo(@RequestParam("cfmpass") String pass,@RequestParam("username") String username,@RequestParam("password") String password,HttpSession session, Model model) {
        Manager user = (Manager) session.getAttribute("user");
        if (user.getPassword().equals(pass)) {
            user.setUsername(username);
            user.setPassword(password);
            Manager updatedUser = repository.save(user);
            ResponseEntity.ok(updatedUser);
        }
        Object user1 = session.getAttribute("user");
        model.addAttribute("user", user);
        return "info";
    }

//    @PostMapping("editinfo")
//    public ResponseEntity<Manager> updateUser(@Valid @RequestBody Manager userDetails) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
//
//        user.setName(userDetails.getName());
//        user.setEmail(userDetails.getEmail());
//        user.setPassword(userDetails.getPassword());
//
//        User updatedUser = userRepository.save(user);
//        return ResponseEntity.ok(updatedUser);
//    }
}

