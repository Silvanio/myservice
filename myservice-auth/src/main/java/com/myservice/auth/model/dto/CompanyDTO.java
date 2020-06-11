package com.myservice.auth.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyDTO {
    private String name;
    private String fiscalNumber;
    private boolean client;
}
