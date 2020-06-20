package com.myservice.common.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyDTO {
    private String name;
    private String fiscalNumber;
    private boolean client;
}
