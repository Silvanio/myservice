package com.myservice.auth.service.impl;

import com.myservice.auth.model.Authority;
import com.myservice.auth.model.User;
import com.myservice.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;


@Service
@Transactional
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] info = validateUser(username);
        return userRepository.findByCodeCompanyAndUsername(info[0],info[1])
                .map(user -> new org.springframework.security.core.userdetails.User(username, user.getPassword(), getGrantedAuthorities(user)))
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " Not found"));
    }

    private String[] validateUser(String username) {
        String[] info = username.split("-");
        if(info.length < 1){
             throw new UsernameNotFoundException("User " + username + " Not found");
        }
        return info;
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(User user) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : user.getAuthorities()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
            grantedAuthorities.add(grantedAuthority);
        }

        return grantedAuthorities;
    }
}