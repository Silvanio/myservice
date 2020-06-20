package com.myservice.common.dto.auth;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class UserDTO {
    private String name;
    private String username;
    private String email;
    private CompanyDTO company;
    private List<String> authorities;
    private List<String> modules;
}
