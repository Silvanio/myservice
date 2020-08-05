package com.myservice.auth.model.dto;

import com.myservice.common.dto.common.MyDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockDTO extends MyDTO {
    private String code;
    private String name;
    private byte[] logo;
    private SegmentDTO segment;
    private ProductDTO product;
}
