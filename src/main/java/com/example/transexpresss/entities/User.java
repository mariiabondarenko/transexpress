package com.example.transexpresss.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    private String password;
    private String roles;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipper_id")
    private Shipper shipper;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrier_id")
    private Carrier carrier;

}
