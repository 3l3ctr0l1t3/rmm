package com.ninja.rmm.resources;

import com.ninja.rmm.exceptions.ApplicationException;
import com.ninja.rmm.models.Service;
import com.ninja.rmm.services.CustomerService;
import com.ninja.rmm.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController extends SecureController{

    private final SubscriptionService subscriptionService;
    private final CustomerService customerService;

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity deleteService (@PathVariable("subscriptionId") Long subscriptionId, Principal principal){
        try {
            var user = getUser(principal);
            subscriptionService.deleteSubscription(subscriptionId, user.getId());
            return ResponseEntity.noContent().build();
        } catch(ApplicationException ae){
            return ResponseEntity.status(ae.getHttpStatus()).body(ae.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity addService (@RequestBody Service service, Principal principal){
            try {
                var user = getUser(principal);
                var subscription = subscriptionService.createSubscription(service.getId(), user.getId());
                return ResponseEntity.ok(subscription);
            } catch(ApplicationException ae){
                return ResponseEntity.status(ae.getHttpStatus()).body(ae.getMessage());
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
    }

    @GetMapping
    public ResponseEntity getAllSubscriptions(Principal principal){
        try {
            var user = getUser(principal);
            var subscription = subscriptionService.getAllByCustomerId(user.getId());
            return ResponseEntity.ok(subscription);
        } catch(ApplicationException ae){
            return ResponseEntity.status(ae.getHttpStatus()).body(ae.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}

