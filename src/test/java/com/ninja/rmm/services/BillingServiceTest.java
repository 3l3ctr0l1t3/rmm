package com.ninja.rmm.services;

import com.ninja.rmm.exceptions.BillingException;
import com.ninja.rmm.models.*;
import com.ninja.rmm.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
class BillingServiceTest {

    private static final String DEVICE_COST = "7";
    private static final Long CUSTOMER_ID = 1l;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    Customer customer;
    @Mock
    Device device;
    @Mock
    Device device4;
    @Mock
    Subscription subscription;
    @Mock
    Subscription subscription2;
    @Mock
    Subscription subscription3;
    @Mock
    Service service;
    @Mock
    Service service2;
    @Mock
    Service service3;
    @Mock
    Price price;
    @Mock
    Price price2;
    @Mock
    SystemType systemType;
    @Mock
    SystemType systemType2;

    @InjectMocks
    BillingService billingService;

    List<Device> customerDevices;
    List<Subscription> subscriptions;

    @BeforeEach
    void setUp() {
        when(price.getType()).thenReturn(systemType);
        when(price.getPrice()).thenReturn( new BigDecimal("5"));

        when(price2.getType()).thenReturn(systemType2);
        when(price2.getPrice()).thenReturn( new BigDecimal("10"));

        when(service3.getPrices()).thenReturn(Arrays.asList(price, price2));
        when(service3.getDefaultPrice()).thenReturn( new BigDecimal("3"));

        when(service2.getPrices()).thenReturn(Arrays.asList(price2));
        when(service2.getDefaultPrice()).thenReturn( new BigDecimal("2"));

        when(service.getPrices()).thenReturn(Arrays.asList());
        when(service.getDefaultPrice()).thenReturn( new BigDecimal("1"));

        when(subscription.getService()).thenReturn(service);
        when(subscription2.getService()).thenReturn(service2);
        when(subscription3.getService()).thenReturn(service3);

        when(customer.getDevices()).thenReturn(customerDevices);
        when(customer.getSubscriptions()).thenReturn(subscriptions);

        when(device.getType()).thenReturn(systemType);
        when(device4.getType()).thenReturn(systemType2);

        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

        ReflectionTestUtils.setField(billingService, "deviceCost", DEVICE_COST);

    }

    @Test
    void calculateMonthlyBillShouldUseGeneralCostWhenNoParticularCostBySysTypeIsSet() throws BillingException {
        when(customer.getDevices()).thenReturn(Arrays.asList(device));
        when(customer.getSubscriptions()).thenReturn(Arrays.asList(subscription));
        var result = billingService.calculateMonthlyBill(CUSTOMER_ID);
        assertEquals(new BigDecimal("8"),result.getTotal());
    }

    @Test
    void calculateMonthlyBillShouldUseMixedCostWhenParticularCostBySysTypeIsSet() throws BillingException {
        when(customer.getDevices()).thenReturn(Arrays.asList(device,device4));
        when(customer.getSubscriptions()).thenReturn(Arrays.asList(subscription,subscription2));
        var result = billingService.calculateMonthlyBill(CUSTOMER_ID);
        assertEquals(new BigDecimal("28"),result.getTotal());
    }

    @Test
    void calculateMonthlyBillShouldUseParticularCostBySysTypeIsSet() throws BillingException {
        when(customer.getDevices()).thenReturn(Arrays.asList(device,device4));
        when(customer.getSubscriptions()).thenReturn(Arrays.asList(subscription3));
        var result = billingService.calculateMonthlyBill(CUSTOMER_ID);
        assertEquals(new BigDecimal("29"),result.getTotal());
    }

    @Test
    void calculateMonthlyBillProperlyThrowsExceptionWhenNoCustomerIsFound() throws BillingException {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());
        var exception = assertThrows(BillingException.class, ()->billingService.calculateMonthlyBill(CUSTOMER_ID));
        assertEquals(exception.getMessage(),"customer not found");
    }
}