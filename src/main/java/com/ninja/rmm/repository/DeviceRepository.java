package com.ninja.rmm.repository;

import com.ninja.rmm.models.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends CrudRepository<Device,Long> {

    List<Device> findAllByCustomerId(Long customerId);
    Optional<Device> findByIdAndCustomerId(Long deviceId, Long customerId );


}
