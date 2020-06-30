package com.myservice.common.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class MyEntity implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dt_register")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateRegister;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private StatusEnum status;

    @PrePersist
    public void preSave() {
        if (dateRegister == null) {
            dateRegister = new Date();
        }
        if (status == null) {
            status = StatusEnum.ACTIVE;
        }
    }
}
