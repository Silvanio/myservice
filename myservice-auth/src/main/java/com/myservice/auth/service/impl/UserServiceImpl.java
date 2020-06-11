package com.myservice.auth.service.impl;

import com.myservice.auth.model.Authority;
import com.myservice.auth.model.User;
import com.myservice.auth.model.dto.CompanyDTO;
import com.myservice.auth.model.dto.UserDTO;
import com.myservice.auth.repository.UserRepository;
import com.myservice.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    public UserDTO loadUserInfoBy(String code, String username) throws UsernameNotFoundException {
        return userRepository.findByCodeCompanyAndUsername(username, code)
                .map(this::parseDTO)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " Not found"));
    }

    private UserDTO parseDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAuthorities(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toList()));
        userDTO.setModules(user.getAuthorities().stream().map(Authority::getModule).collect(Collectors.toList()));
        userDTO.setModules(userDTO.getModules().stream().distinct().collect(Collectors.toList()));
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setClient(user.getCompany().isClient());
        companyDTO.setFiscalNumber(user.getCompany().getFiscalNumber());
        companyDTO.setName(user.getName());
        userDTO.setCompany(companyDTO);
        return userDTO;
    }
}
