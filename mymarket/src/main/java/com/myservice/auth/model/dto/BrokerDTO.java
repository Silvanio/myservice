package com.myservice.auth.model.dto;

import com.myservice.common.dto.common.MyDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BrokerDTO extends MyDTO {
    private Integer code;
    private String name;
}
