package com.myservice.auth.controller;

import com.myservice.auth.service.UserService;
import com.myservice.common.dto.auth.UserDTO;
import com.myservice.common.dto.common.ResponseMessageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/")
@Api(value = "AUTH API - Autenticação")
public class AuthController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "Obter informações do usuário logado", response = UserDTO.class)
    @PostMapping("/currentUserInfo")
    public UserDTO getCurrentUserInfo(Principal user) {
        String[] info = user.getName().split("-");
        return userService.loadUserInfoBy(info[0], info[1]);
    }

    @ApiOperation(value = "Esqueci minha senha", response = ResponseMessageDTO.class)
    @PostMapping("/forgotPassword/{codeCompany}/{username}")
    public ResponseMessageDTO forgotPassword(@PathVariable String codeCompany, @PathVariable String username) {
        userService.forgotPassword(codeCompany, username);
        return ResponseMessageDTO.get("msg_general_success");
    }

}