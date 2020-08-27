package com.myservice.mymarket.model.dto.marktstack;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MarketStackDTO {
    private MarketStackPage pagination;
    private List<MarketStackData> data;
}
