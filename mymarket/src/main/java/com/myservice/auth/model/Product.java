package com.myservice.auth.model;

import com.myservice.auth.model.enumerator.TypeProduct;
import com.myservice.common.domain.MyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
@Table(name = "tb_product", schema = "market")
public class Product extends MyEntity {

    private String code;
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "msg_required_fields")
    private TypeProduct type;

}
