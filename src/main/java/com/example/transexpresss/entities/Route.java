package com.example.transexpresss.entities;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "route")
@Data
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String shippingPoint;
    private String deliveryPoint;
    private Date shippingDate;
    private Date deliveryDate;

    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL,
            mappedBy = "route")
    private Cargo cargo;

}
