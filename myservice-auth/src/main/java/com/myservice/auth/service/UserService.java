package com.myservice.auth.service;

import com.myservice.auth.model.dto.UserDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    UserDTO loadUserInfoBy(String code,String username) throws UsernameNotFoundException;
}
