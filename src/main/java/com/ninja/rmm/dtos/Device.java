package com.ninja.rmm.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Device {
    private Long id;
    private String systemName;
    @JsonIgnore
    private Customer customer;
    @JsonProperty(value = "systemType")
    private SystemType type;
}
