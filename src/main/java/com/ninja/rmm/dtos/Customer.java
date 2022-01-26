package com.ninja.rmm.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import lombok.Data;

import java.util.Collection;

@Data
public class Customer {

    private Long id;
    private String alias;
    private Collection<Subscription> subscriptions = new ArrayList<>();
    private Collection<Device> devices = new ArrayList<>();
    private String user;
    @JsonIgnore
    private String password;

}
