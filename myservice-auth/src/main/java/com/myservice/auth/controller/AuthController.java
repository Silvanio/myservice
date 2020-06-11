package com.myservice.auth.controller;

import com.myservice.auth.model.dto.UserDTO;
import com.myservice.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class AuthController {

    @Autowired
    UserService userService;

    @RequestMapping("/currentUserInfo")
    public UserDTO getCurrentUserInfo(Principal user) {
        String[] info = user.getName().split("-");
        return userService.loadUserInfoBy(info[0], info[1]);
    }

}