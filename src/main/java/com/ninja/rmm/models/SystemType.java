package com.ninja.rmm.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "system_type")
public class SystemType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String type;
}
