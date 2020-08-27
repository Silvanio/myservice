package com.myservice.mymarket.model;


import com.myservice.common.domain.MyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
@Table(name = "tb_track_market_user", schema = "market")
public class TrackMarketUser extends MyEntity {

    @ManyToOne
    @JoinColumn(name = "id_stock")
    @NotNull(message = "msg_required_fields")
    private Stock stock;

    @Column(name = "xid_user")
    private Long xidUser;

}
