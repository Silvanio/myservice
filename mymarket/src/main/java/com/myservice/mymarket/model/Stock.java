package com.myservice.mymarket.model;


import com.myservice.common.domain.MyEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
@Table(name = "tb_stock", schema = "market")
public class Stock extends MyEntity {

    @Column(name = "code")
    @NotBlank(message = "msg_required_fields")
    private String code;

    @Column(name = "name")
    @NotBlank(message = "msg_required_fields")
    private String name;

    private String exchange;

    @Column(name = "isin")
    private String isin;

    @Column(name = "specification_code")
    private String specificationCode;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] logo;

    @ManyToOne
    @JoinColumn(name = "id_segment")
    private Segment segment;

    @ManyToOne
    @JoinColumn(name = "id_product")
    @NotNull(message = "msg_required_fields")
    private Product product;

    @Column(name = "refresh_quote" )
    private Boolean refreshQuote;

    @Transient
    public boolean isStockRefreshQuote(){
        return refreshQuote != null && refreshQuote;
    }

}
