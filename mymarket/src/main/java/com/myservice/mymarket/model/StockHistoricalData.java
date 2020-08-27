package com.myservice.mymarket.model;

import com.myservice.common.domain.MyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "tb_stock_historical_data", schema = "market")
public class StockHistoricalData extends MyEntity {

    @Column(name = "code_stock")
    private String codeStock;

    @Column(name = "dt_quote")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateQuote;

    private String exchange;
    private BigDecimal open;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal close;
    private BigDecimal volume;


}
