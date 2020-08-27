package com.myservice.mymarket.model;

import com.myservice.common.domain.MyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "tb_broker", schema = "market")
public class Broker extends MyEntity {
    private Integer code;
    private String name;
}
