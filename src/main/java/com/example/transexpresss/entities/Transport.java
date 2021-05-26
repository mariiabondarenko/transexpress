package com.example.transexpresss.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "transport")
@Data
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String brand;
    private String type;
    private Float carrying;
    private Float capacity;
    private Float length;
    private Float width;
    private Float height;
    private String loadingType;
    private String region;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrier_id", nullable = false)
    private Carrier carrier;

    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL,
            mappedBy = "transport")
    private Request request;
}
