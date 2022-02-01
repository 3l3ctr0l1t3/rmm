package com.ninja.rmm.models;


import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Data
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(nullable = false)
    private BigDecimal defaultPrice;
    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    private Collection<Subscription> subscriptions;
    @OneToMany(mappedBy = "service", fetch = FetchType.EAGER)
    private Collection<Price> prices;
}



