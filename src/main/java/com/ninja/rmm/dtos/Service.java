package com.ninja.rmm.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Collection;

@Data
public class Service {
    private Long id;
    private String name;
    private BigDecimal price;
    private Collection<Subscription> subscriptions;
}
