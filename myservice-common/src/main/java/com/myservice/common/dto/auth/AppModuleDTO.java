package com.myservice.common.dto.auth;

import com.myservice.common.dto.IDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppModuleDTO implements IDTO {
    private Long id;
    private String name;
    private String description;
    private String webUrl;
}
