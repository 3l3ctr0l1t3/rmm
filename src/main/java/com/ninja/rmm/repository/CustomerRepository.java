package com.ninja.rmm.repository;

import com.ninja.rmm.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
    Optional<Customer> findByEmailAndPassword(String email, String password);
    Optional<Customer> findByEmail(String email);
}
