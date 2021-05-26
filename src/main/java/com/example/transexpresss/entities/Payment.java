package com.example.transexpresss.entities;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "payment")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Float amount;
    private String type;
    private String moment;
    private String currency;
    private String dimention;

    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL,
            mappedBy = "payment")
    private Cargo cargo;


}
