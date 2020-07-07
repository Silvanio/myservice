package com.myservice.common.dto.auth;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorityDTO {

    private String name;
    private String shortDescription;
    private String description;
    private boolean client;
}
