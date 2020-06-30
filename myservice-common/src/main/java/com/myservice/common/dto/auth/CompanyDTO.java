package com.myservice.common.dto.auth;

import com.myservice.common.dto.MyDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyDTO extends MyDTO {
    private String code;
    private String name;
    private String fiscalNumber;
    private String address;
    private String comments;
    private boolean client;
}
