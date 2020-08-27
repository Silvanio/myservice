package com.myservice.mymarket.model;

import com.myservice.common.domain.MyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
@Table(name = "tb_segment", schema = "market")
public class Segment extends MyEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "id_sub_sector", nullable = false)
    @NotNull(message = "msg_required_fields")
    private SubSector subSector;

}
