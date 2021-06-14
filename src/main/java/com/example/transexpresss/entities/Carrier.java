package com.example.transexpresss.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "carrier")
@Data
public class Carrier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String region;
    private String businessEntity;
    private String edrpouCode;
    private String nameOfLegalEntity;
    private Date registrationDate;

    @OneToMany(mappedBy = "carrier", cascade = CascadeType.ALL)
    private List<Transport> transports;

    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL,
            mappedBy = "carrier")
    private User user;
}
