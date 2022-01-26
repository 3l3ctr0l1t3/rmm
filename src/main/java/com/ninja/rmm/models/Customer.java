package com.ninja.rmm.models;

import java.util.ArrayList;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String alias;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Collection<Device> devices = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Collection<Subscription> subscriptions = new ArrayList<>();
    @Column(unique = true)
    private String email;
    private String password;

    public void addDevice(Device device){
        this.devices.add(device);
    }

    public void addSubscription(Subscription subscription){
        this.subscriptions.add(subscription);
    }

}
