package com.ninja.rmm.services;

import com.ninja.rmm.dtos.Customer;
import com.ninja.rmm.dtos.Device;
import com.ninja.rmm.dtos.Subscription;
import com.ninja.rmm.exceptions.CustomerException;
import com.ninja.rmm.repository.CustomerRepository;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.FetchType;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final DeviceService deviceService;

    public Customer getCustomerLogIn(String email, String password) throws CustomerException {

        var customer =customerRepository.findByEmailAndPassword(email, password);
        if (customer.isPresent()){
            return getCustomerFromModel(customer.get(), FetchType.LAZY);
        }
        throw new CustomerException("Customer not found", HttpStatus.UNAUTHORIZED);
    }

    public Customer getCustomerByEmail(String email) throws CustomerException {

        var customer =customerRepository.findByEmail(email);
        if (customer.isPresent()){
            return getCustomerFromModel(customer.get(), FetchType.LAZY);
        }
        throw new CustomerException("Customer not found", HttpStatus.UNAUTHORIZED);
    }

    public Optional<Customer> updateCustomer(Customer device) {
        return customerRepository.findById(device.getId())
            .map(deviceModel -> {
                BeanUtils.copyProperties(device, deviceModel);
                return deviceModel;
            })
            .map(customerRepository::save).map(CustomerService::getCustomerFromModel);
    }

    public static Customer getCustomerFromModel(com.ninja.rmm.models.Customer customerModel){
        return getCustomerFromModel(customerModel, FetchType.EAGER);
    }

    public static Customer getCustomerFromModel(com.ninja.rmm.models.Customer customerModel, FetchType fetchType){
        var customer = new Customer();
        BeanUtils.copyProperties(customerModel,customer);
        if(fetchType.equals(FetchType.EAGER)) {
            var deviceList =
                    customerModel.getDevices()
                            .stream()
                            .map(DeviceService::getDeviceFromModel)
                            .collect(
                                    Collectors.toList());
            customer.setDevices(deviceList);
        }
        return customer;
    }

    public static com.ninja.rmm.models.Customer getCustomerFromDto(Customer customerModel){
        var customer = new com.ninja.rmm.models.Customer();
        BeanUtils.copyProperties(customerModel,customer);
        var deviceList =
            customerModel.getDevices()
                .stream()
                .map(DeviceService::getDeviceFromDto)
                .collect(
                    Collectors.toList());
        customer.setDevices(deviceList);
        return customer;
    }
}
