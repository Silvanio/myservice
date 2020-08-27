package com.myservice.mymarket.model.dto;

import com.myservice.common.dto.common.MyDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TrackMarketUserDTO extends MyDTO {
    private StockDTO stock;
    private Long xidUser;
}
