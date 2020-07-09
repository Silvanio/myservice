package com.myservice.auth.service.impl;

import com.myservice.auth.model.*;
import com.myservice.auth.repository.ContractRepository;
import com.myservice.auth.repository.UserRepository;
import com.myservice.common.domain.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContractRepository contractRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] info = validateUser(username);
        return userRepository.findByCodeCompanyAndUsernameAndStatus(info[0], info[1], StatusEnum.ACTIVE)
                .map(user -> new org.springframework.security.core.userdetails.User(username, user.getPassword(), getGrantedAuthorities(user)))
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " Not found"));
    }

    private String[] validateUser(String username) {
        String[] info = username.split("-");
        if (info.length < 1) {
            throw new UsernameNotFoundException("User " + username + " Not found");
        }
        return info;
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(User user) {
        HashSet<String> modules = getContractModules(user);
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : user.getAuthorities()) {
            if (modules.contains(authority.getAppModule().getName())) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
                grantedAuthorities.add(grantedAuthority);
            }
        }
        return grantedAuthorities;
    }

    private HashSet<String> getContractModules(User user) {
        Company company = user.getCompany();
        List<Contract> contracts = contractRepository.findContractByCompanyIdAndStatusAndValidate( StatusEnum.ACTIVE,company.getId(), new Date());
        HashSet<String> modules = new HashSet<>();
        for (Contract contract : contracts) {
            List<String> moduleContract = contract.getAppModules().stream().map(AppModule::getName).collect(Collectors.toList());
            modules.addAll(moduleContract);
        }
        return modules;
    }
}