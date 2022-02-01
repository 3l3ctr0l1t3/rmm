package com.ninja.rmm.services;

import com.ninja.rmm.dtos.Bill;
import com.ninja.rmm.dtos.BillDetail;
import com.ninja.rmm.models.Device;
import com.ninja.rmm.models.Subscription;
import com.ninja.rmm.models.SystemType;
import com.ninja.rmm.exceptions.BillingException;
import com.ninja.rmm.models.Price;
import com.ninja.rmm.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingService {

    @Value("${configuration.device_cost}")
    private String deviceCost;
    private final CustomerRepository customerRepository;

    public Bill calculateMonthlyBill(Long customerId) throws BillingException {
        var finalBill = new Bill();
      var customer = customerRepository.findById(customerId).orElseThrow(()->new BillingException("customer not found", HttpStatus.BAD_REQUEST));
      var devices = customer.getDevices();
      var services = customer.getSubscriptions().stream().map(Subscription::getService);
      var devicesBySystemType = devices.stream().collect(Collectors.groupingBy(Device::getType, Collectors.counting()));
        services.forEach(service -> finalBill.addDetail(service.getName(), calculateServiceFee(devicesBySystemType ,service)));
        finalBill.addDetail("devices", new BigDecimal(devices.size()).multiply(new BigDecimal(deviceCost)));
        finalBill.setTotal(finalBill.getDetails().stream()
              .map(BillDetail::getTotal)
              .reduce(BigDecimal.ZERO,BigDecimal::add));
      return finalBill;
    }

    private BigDecimal calculateServiceFee(Map<SystemType, Long> devices, com.ninja.rmm.models.Service service){
        BigDecimal serviceFee = new BigDecimal(0);
        for(SystemType sysType :devices.keySet()){
            var appliedCost = service.getPrices().stream()
                    .filter(price -> price.getType().equals(sysType))
                    .map(Price::getPrice)
                    .findFirst()
                    .orElse(service.getDefaultPrice());
            serviceFee =serviceFee.add(appliedCost.multiply(BigDecimal.valueOf(devices.get(sysType))));
        }
        return serviceFee;

    }
}
