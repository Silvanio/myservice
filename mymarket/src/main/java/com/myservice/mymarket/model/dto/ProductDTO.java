package com.myservice.mymarket.model.dto;

import com.myservice.mymarket.model.enumerator.TypeProduct;
import com.myservice.common.dto.common.MyDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDTO extends MyDTO {
    private String code;
    private String name;
    private TypeProduct type;
}
