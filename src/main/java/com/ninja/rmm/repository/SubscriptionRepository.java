package com.ninja.rmm.repository;

import com.ninja.rmm.models.Subscription;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<Subscription,Long> {

    Collection<Subscription> findByCustomerId(Long customerId);
    Optional<Subscription> findByIdAndCustomerId(Long id, Long customerId);
    Collection<Subscription> findAllByCustomerId(Long customerId);
}
