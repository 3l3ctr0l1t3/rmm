package com.ninja.rmm.resources;

import com.ninja.rmm.dtos.Device;
import com.ninja.rmm.exceptions.ApplicationException;
import com.ninja.rmm.exceptions.CustomerException;
import com.ninja.rmm.services.CustomerService;
import com.ninja.rmm.services.DeviceService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.security.Principal;


@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController extends SecureController{

  private final DeviceService deviceService;


  @GetMapping
  public ResponseEntity getDevices(Principal principal) {
    try {
      var user = getUser(principal);
      var devices = deviceService.getDevices(user.getId());
      return ResponseEntity.ok(devices);
    } catch(ApplicationException ae){
      return ResponseEntity.status(ae.getHttpStatus()).body(ae.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/{deviceId}")
  public ResponseEntity getDevice(@PathVariable("deviceId") Long deviceId, Principal principal) {
    try {
      var user = getUser(principal);
      var device = deviceService.getDevice(deviceId, user.getId());
      if (device.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
      } else {
        return ResponseEntity.ok(device.get());
      }
    } catch(ApplicationException ae){
      return ResponseEntity.status(ae.getHttpStatus()).body(ae.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity createDevice(@RequestBody Device device, Principal principal) {
    try {
      var customer = getUser(principal);
      device.setCustomer(customer);
      device.setId(null);
      return ResponseEntity.status(HttpStatus.OK)
          .body(deviceService.createDevice(device));

    } catch(ApplicationException ae){
      return ResponseEntity.status(ae.getHttpStatus()).body(ae.getMessage());
    }  catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @DeleteMapping("/{deviceId}")
  public ResponseEntity deleteDevice(@PathVariable("deviceId") Long deviceId, Principal principal) {
    try {
      var user = getUser(principal);
      var device = deviceService.getDevice(deviceId, user.getId());
      if (!device.isEmpty()) {
        deviceService.deleteDevice(deviceId);
        return ResponseEntity.noContent().build();
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
      }
    } catch(ApplicationException ae){
      return ResponseEntity.status(ae.getHttpStatus()).body(ae.getMessage());
    }  catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PutMapping("/{deviceId}")
  public ResponseEntity updateDevice(@PathVariable("deviceId") Long deviceId,
      @RequestBody Device device, Principal principal) {
    try {
      var user = getUser(principal);

      if (device.getId() != null && !device.getId().equals(deviceId)) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body("Update operation can change deviceId");
      }

      if (deviceService.getDevice(deviceId, user.getId()).isPresent()) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(deviceService.updateDevice(device));
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Device not found for current user");
      }
    } catch(ApplicationException ae){
      return ResponseEntity.status(ae.getHttpStatus()).body(ae.getMessage());
    }  catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

}
