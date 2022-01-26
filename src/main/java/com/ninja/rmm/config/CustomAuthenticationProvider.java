package com.ninja.rmm.config;

import com.ninja.rmm.dtos.Customer;
import com.ninja.rmm.services.CustomerService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomerService customerService;

    @Transactional
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        try{
            customerService.getCustomerLogIn(email, password);
        }catch(Exception e){
            throw new AuthenticationCredentialsNotFoundException("user not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        return new UsernamePasswordAuthenticationToken(email, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}