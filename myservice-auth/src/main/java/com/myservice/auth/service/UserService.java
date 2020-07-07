package com.myservice.auth.service;

import com.myservice.auth.model.User;
import com.myservice.common.dto.auth.UserDTO;
import com.myservice.common.service.IService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends IService<Long, User, UserDTO> {

    UserDTO loadUserInfoBy(String code,String username) throws UsernameNotFoundException;

    void forgotPassword(String codeCompany, String username);

    void changePassword(UserDTO user);

    void updateUser(UserDTO user);
}
