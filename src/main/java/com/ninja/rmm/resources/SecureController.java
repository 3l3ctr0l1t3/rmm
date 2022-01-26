package com.ninja.rmm.resources;

import com.ninja.rmm.dtos.Customer;
import com.ninja.rmm.exceptions.CustomerException;
import com.ninja.rmm.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;


public abstract class SecureController {

    @Autowired
    private CustomerService customerService;

    public Customer getUser(Principal principal) throws CustomerException {
        String email = principal.getName();
        return customerService.getCustomerByEmail(email);
    }
}
