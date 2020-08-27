package com.myservice.mymarket.model.dto.marktstack;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MarketStackPage {
    private Integer limit;
    private Integer offset;
    private Integer count;
    private Integer total;
}
