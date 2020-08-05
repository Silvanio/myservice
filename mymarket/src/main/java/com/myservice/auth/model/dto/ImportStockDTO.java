package com.myservice.auth.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImportStockDTO {
    private String smallCode;
    private String code;
    private String segment;
    private String product;
    private String isin;
    private String specificationCode;
    private String nome;
}
