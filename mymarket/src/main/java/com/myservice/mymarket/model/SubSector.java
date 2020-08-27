package com.myservice.mymarket.model;

import com.myservice.common.domain.MyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
@Table(name = "tb_sub_sector", schema = "market")
public class SubSector extends MyEntity {

    @NotBlank(message = "msg_required_fields")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_sector")
    @NotNull(message = "msg_required_fields")
    private Sector sector;

}
