package com.swp.g3.config;

import com.swp.g3.filter.JwtAuthenticationEntryPoint;
import com.swp.g3.filter.JwtRequestFilter;
import com.swp.g3.service.JwtManagerDetailsService;
import com.swp.g3.service.JwtStaffDetailsService;
import com.swp.g3.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtStaffDetailsService jwtStaffDetailsService;
    @Autowired
    private JwtManagerDetailsService jwtManagerDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
        auth.userDetailsService(jwtStaffDetailsService).passwordEncoder(passwordEncoder());
        auth.userDetailsService(jwtManagerDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean()
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf()
                .disable()
                .authorizeRequests().antMatchers("/api/customer/login").permitAll()
                .and().authorizeRequests().antMatchers("/api/staff/login").permitAll()
                .and().authorizeRequests().antMatchers("/api/manager/login").permitAll()
                .and().authorizeRequests().antMatchers("/api/customer/verify").permitAll()
                .and().authorizeRequests().antMatchers("/api/customer/register").permitAll()
                .and().authorizeRequests().antMatchers("/api/customer/password/**").permitAll()
                .and().authorizeRequests().antMatchers("/api/manager/**").hasAuthority("manager")
                .and().authorizeRequests().antMatchers("/api/staff/**").hasAuthority("staff")
                .and().authorizeRequests().antMatchers("/api/customer/**").hasAuthority("customer")
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
            configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
