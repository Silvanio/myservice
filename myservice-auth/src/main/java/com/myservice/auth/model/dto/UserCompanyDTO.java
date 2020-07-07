package com.myservice.auth.model.dto;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Setter
@Getter
public class UserCompanyDTO implements Serializable {
    private Long idUser;
    private Long idCompany;
}
