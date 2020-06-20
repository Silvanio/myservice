package com.myservice.auth.service;

import com.myservice.common.dto.auth.UserDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    UserDTO loadUserInfoBy(String code,String username) throws UsernameNotFoundException;

    void forgotPassword(String codeCompany, String username);
}
