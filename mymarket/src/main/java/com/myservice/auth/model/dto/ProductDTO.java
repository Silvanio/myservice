package com.myservice.auth.model.dto;

import com.myservice.auth.model.enumerator.TypeProduct;
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
