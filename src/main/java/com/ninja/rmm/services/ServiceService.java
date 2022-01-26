package com.ninja.rmm.services;

import com.ninja.rmm.dtos.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;


@RequiredArgsConstructor
public class ServiceService {

    public static Service getCustomerFromModel(com.ninja.rmm.models.Service serviceModel){
        var service = new Service();
        BeanUtils.copyProperties(serviceModel,service);
        return service;
    }
}
