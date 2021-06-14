package com.example.transexpresss.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "shipper")
@Data
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String address;
    private Date registrationDate;

    @OneToMany(mappedBy = "shipper", cascade = CascadeType.ALL)
    private List<Cargo> cargoes;

   @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL,
            mappedBy = "shipper")
    private User user;
}
