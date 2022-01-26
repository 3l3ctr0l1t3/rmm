package com.ninja.rmm.services;

import com.ninja.rmm.dtos.Device;
import com.ninja.rmm.dtos.SystemType;
import com.ninja.rmm.exceptions.DeviceException;
import com.ninja.rmm.repository.CustomerRepository;
import com.ninja.rmm.repository.DeviceRepository;
import com.ninja.rmm.repository.SystemTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final CustomerRepository customerRepository;
    private final SystemTypeRepository systemTypeRepository;

    public Collection<Device> getDevices(Long customerId) {
        return deviceRepository.findAllByCustomerId(customerId).stream().map(DeviceService::getDeviceFromModel).collect(Collectors.toList());
    }

    public Optional<Device> getDevice(Long deviceId, Long customerId) {
        return deviceRepository.findByIdAndCustomerId(deviceId, customerId).map(DeviceService::getDeviceFromModel);
    }

    public Optional<Device> updateDevice(Device device) {
        return deviceRepository.findById(device.getId())
                .map(deviceModel -> {
                    BeanUtils.copyProperties(device, deviceModel);
                    return deviceModel;
                })
                .map(deviceRepository::save).map(DeviceService::getDeviceFromModel);
    }

    public Device createDevice(Device device) throws DeviceException {
        var deviceModel = getDeviceFromDto(device);
        var customer = customerRepository.findById(device.getCustomer().getId())
                .orElseThrow(()-> new DeviceException("customer does not exist", HttpStatus.BAD_REQUEST));
        var sysType = systemTypeRepository.findById(device.getType().getId())
                .orElseThrow(()-> new DeviceException("system type does not exist", HttpStatus.BAD_REQUEST));
        deviceModel.setCustomer(customer);
        deviceModel.setType(sysType);
        return getDeviceFromModel(deviceRepository.save(deviceModel));
    }

    public void deleteDevice(Long deviceId) {
        var device = deviceRepository.findById(deviceId);
        device.ifPresent(deviceRepository::delete);
    }

    public static Device getDeviceFromModel(com.ninja.rmm.models.Device deviceModel) {
        var device = new Device();
        BeanUtils.copyProperties(deviceModel, device);
        var systemType = new SystemType();
        BeanUtils.copyProperties(deviceModel.getType(), systemType);
        device.setType(systemType);
        return device;
    }

    public static com.ninja.rmm.models.Device getDeviceFromDto(Device device) {
        var deviceModel = new com.ninja.rmm.models.Device();
        BeanUtils.copyProperties(device, deviceModel);
        var systemType = new com.ninja.rmm.models.SystemType();
        BeanUtils.copyProperties(device.getType(), systemType);
        deviceModel.setType(systemType);
        return deviceModel;
    }


}
