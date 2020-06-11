package com.myservice.auth.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "company", schema = "auth")
@Entity
@Setter
@Getter
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Column(nullable = false)
    private String name;

    @Column(name = "fiscal_number")
    private String fiscalNumber;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean client;
}