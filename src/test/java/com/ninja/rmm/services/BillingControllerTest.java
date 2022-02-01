package com.ninja.rmm.services;

import com.ninja.rmm.dtos.Bill;
import com.ninja.rmm.dtos.Customer;
import com.ninja.rmm.exceptions.BillingException;
import com.ninja.rmm.exceptions.CustomerException;
import com.ninja.rmm.resources.BillingController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillingControllerTest {

    private static final String USER_EMAIL = "email@foo.com";
    private static final Long CUSTOMER_ID = 10L;

    @Mock
    Customer customer;
    @Mock
    Principal principal;
    @Mock
    BillingService billingService;
    @Mock
    Bill bill;
    @Mock
    CustomerService customerService;

    @InjectMocks
    BillingController billingController;

    @BeforeEach
    public void setUp() throws CustomerException {
        doReturn(USER_EMAIL).when(principal).getName();
        doReturn(customer).when(customerService).getCustomerByEmail(USER_EMAIL);
        doReturn(CUSTOMER_ID).when(customer).getId();

        ReflectionTestUtils.setField(billingController, "customerService", customerService);
    }

    @Test
    void getMonthlyBill() throws BillingException {
        when(billingService.calculateMonthlyBill(eq(customer.getId()))).thenReturn(bill);

        var returnedBill = billingController.getMonthlyBill(principal);

        assertEquals(ResponseEntity.ok(bill), returnedBill);
    }

    @Test
    void getMonthlyBillApplicationException() throws BillingException {
        when(billingService.calculateMonthlyBill(eq(customer.getId()))).thenThrow(new BillingException("billing error", HttpStatus.BAD_REQUEST));

        var exception = billingController.getMonthlyBill(principal);

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("billing error"), exception);
    }

    @Test
    void getMonthlyBillInternalServerError() throws BillingException {
        var runtimeExc = new RuntimeException("Error");
        when(billingService.calculateMonthlyBill(eq(customer.getId()))).thenThrow(runtimeExc);
        var exception = billingController.getMonthlyBill(principal);

        assertEquals(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error"), exception);
    }
}