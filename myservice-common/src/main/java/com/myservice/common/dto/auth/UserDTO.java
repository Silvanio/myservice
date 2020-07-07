package com.myservice.common.dto.auth;

import com.myservice.common.dto.common.MyDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDTO extends MyDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private String newPassword;
    private CompanyDTO company;
    private List<String> authorities;
    private List<AppModuleDTO> appModules;
}
