package com.ninja.rmm.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Subscription {
    private Long id;
    @JsonIgnore
    private Customer customer;
    private Service service;
}
