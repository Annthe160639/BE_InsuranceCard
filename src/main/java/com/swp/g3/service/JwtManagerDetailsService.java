package com.swp.g3.service;

import com.swp.g3.entity.Customer;
import com.swp.g3.entity.Manager;
import com.swp.g3.repository.CustomerRepository;
import com.swp.g3.util.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class JwtManagerDetailsService implements UserDetailsService {
    @Autowired
    ManagerService managerService;
    @Autowired
    public Crypto crypto;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Manager c = managerService.findOneByUsername(username);
        if (c != null) {
            try {
                return new User(c.getUsername(), new BCryptPasswordEncoder().encode(crypto.decrypt(c.getPassword())),
                        Collections.singleton(new SimpleGrantedAuthority(c.getRole())));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
