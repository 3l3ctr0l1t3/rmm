package com.ninja.rmm.services;

import com.ninja.rmm.dtos.Subscription;
import com.ninja.rmm.exceptions.SubscriptionException;
import com.ninja.rmm.repository.CustomerRepository;
import com.ninja.rmm.repository.ServiceRepository;
import com.ninja.rmm.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.FetchType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;
    private final ServiceRepository serviceRepository;

    public Subscription createSubscription(Long serviceId, Long customerId) throws SubscriptionException{
        if (subscriptionRepository.findAllByCustomerId(customerId).stream().anyMatch(subscription1 -> Objects.equals(subscription1.getService().getId(), serviceId))){
            throw  new SubscriptionException("customer already subscribed to that service",HttpStatus.CONFLICT);
        }
        var subscription = new com.ninja.rmm.models.Subscription();
        var customer = customerRepository.findById(customerId)
                .orElseThrow(()->new SubscriptionException("customer not found", HttpStatus.BAD_REQUEST));
        var service = serviceRepository.findById(serviceId)
                .orElseThrow(()->new SubscriptionException("service not found", HttpStatus.BAD_REQUEST));

        subscription.setCustomer(customer);
        subscription.setService(service);
        return subscriptionToDto(subscriptionRepository.save(subscription));

    }

    public List<Subscription> getAllByCustomerId(Long customerId) throws SubscriptionException {
        return subscriptionRepository.findAllByCustomerId(customerId)
                .stream().map(SubscriptionService::subscriptionToDto).collect(Collectors.toList());
    }

    public void deleteSubscription(Long subscriptionId, Long customerId) throws SubscriptionException {
        var subscription = subscriptionRepository.findByIdAndCustomerId(subscriptionId,customerId)
                .orElseThrow(()-> new SubscriptionException("subscription not found", HttpStatus.NOT_FOUND));
        subscriptionRepository.delete(subscription);
    }

    public static Subscription subscriptionToDto(com.ninja.rmm.models.Subscription subscriptionModel){
        var subscription = new Subscription();
        BeanUtils.copyProperties(subscriptionModel,subscription);
        subscription.setCustomer(CustomerService.getCustomerFromModel(subscriptionModel.getCustomer(), FetchType.LAZY));
        subscription.setService(ServiceService.getCustomerFromModel(subscriptionModel.getService()));
        return subscription;
    }

}
