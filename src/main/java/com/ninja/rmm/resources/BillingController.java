package com.ninja.rmm.resources;

import com.ninja.rmm.dtos.Device;
import com.ninja.rmm.exceptions.ApplicationException;
import com.ninja.rmm.services.BillingService;
import com.ninja.rmm.services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/billing")
@RequiredArgsConstructor
public class BillingController extends SecureController {

    private final BillingService billingService;

    @GetMapping
    public ResponseEntity getMonthlyBill(Principal principal) {
        try {
            var user = getUser(principal);
            return ResponseEntity.ok(billingService.calculateMonthlyBill(user.getId()));
        } catch (ApplicationException ae) {
            return ResponseEntity.status(ae.getHttpStatus()).body(ae.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
