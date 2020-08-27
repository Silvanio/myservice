package com.myservice.mymarket.model;

import com.myservice.mymarket.model.enumerator.CodeParamEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "tb_market_param", schema = "market")
public class MarketParam implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "msg_required_fields")
    private CodeParamEnum code;

    @NotNull(message = "msg_required_fields")
    private String name;

    @NotNull(message = "msg_required_fields")
    private String value;
}
