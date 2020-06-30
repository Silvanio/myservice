package com.myservice.common.dto;

import com.myservice.common.domain.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class MyDTO implements IDTO {

    private Long id;
    private Date dateRegister;
    private StatusEnum status;
}
