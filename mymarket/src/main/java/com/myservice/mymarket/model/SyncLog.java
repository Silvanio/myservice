package com.myservice.mymarket.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "tb_sync_log", schema = "market")
public class SyncLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;

    @Column(name = "dt_start")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date start;

    @Column(name = "dt_finish")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date finish;

    private Boolean success;
}
