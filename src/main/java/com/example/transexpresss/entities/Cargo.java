package com.example.transexpresss.entities;

import lombok.Data;
import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "cargo")
@Data
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Float weight;
    private Float capacity;
    private String loadingType;
    private Float length;
    private Float width;
    private Float height;
    private String tare;
    private String additionalInfo;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipper_id", nullable = false)
    private Shipper shipper;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL,
            mappedBy = "cargo")
    private Request request;

}
