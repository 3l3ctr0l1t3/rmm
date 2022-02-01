package com.ninja.rmm.models;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "system_type_id")
    private SystemType type;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

}
