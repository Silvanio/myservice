package com.myservice.auth.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "authority", schema = "auth")
@Setter
@Getter
public class Authority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    private String name;

    @Column(name = "short_description")
    private String shortDescription;

    private String description;

    @ManyToOne
    @JoinColumn(name="id_app_module",nullable = false)
    private AppModule appModule;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean client;
}