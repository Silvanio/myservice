package com.myservice.auth.controller;

import com.myservice.auth.service.UserService;
import com.myservice.common.dto.ResponseMessageDTO;
import com.myservice.common.dto.auth.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api(value = "AUTH API - User")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "Alterar Senha", response = ResponseMessageDTO.class)
    @PostMapping("/changePassword")
    public ResponseMessageDTO changePassword(@RequestBody UserDTO user) {
        userService.changePassword(user);
        return new ResponseMessageDTO("msg_general_success");
    }

    @ApiOperation(value = "Alterar Senha", response = ResponseMessageDTO.class)
    @PostMapping("/update")
    public ResponseMessageDTO update(@RequestBody UserDTO user) {
        userService.update(user);
        return new ResponseMessageDTO("msg_general_success");
    }

    @ApiOperation(value = "Alterar Senha", response = ResponseMessageDTO.class)
    @PostMapping("/teste")
    public ResponseMessageDTO teste() {
        return new ResponseMessageDTO("msg_general_success");
    }


}