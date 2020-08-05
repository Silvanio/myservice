package com.myservice.auth.model;

import com.myservice.common.domain.MyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "tb_sector", schema = "market")
public class Sector extends MyEntity {
    private String name;
}
