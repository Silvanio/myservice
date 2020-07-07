package com.myservice.auth.controller;

import com.myservice.auth.model.AppModule;
import com.myservice.auth.model.Authority;
import com.myservice.auth.model.User;
import com.myservice.auth.service.UserService;
import com.myservice.common.controller.MyController;
import com.myservice.common.dto.auth.AppModuleDTO;
import com.myservice.common.dto.auth.AuthorityDTO;
import com.myservice.common.dto.auth.UserDTO;
import com.myservice.common.dto.common.ResponseMessageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
@Api(value = "AUTH API - User")
public class UserController extends MyController<Long, User, UserDTO> {

    @Autowired
    UserService userService;

    @Override
    public void permitAuthorities(List<String> authorities) {
        authorities.add("AUTH_ADMIN");
        authorities.add("AUTH_CLIENT_ADMIN");
    }

    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Alterar Senha", response = ResponseMessageDTO.class)
    @PostMapping("/changePassword")
    public ResponseMessageDTO changePassword(@RequestBody UserDTO user) {
        userService.changePassword(user);
        return ResponseMessageDTO.get("msg_general_success");
    }

    @ApiOperation(value = "Update Meus Dados", response = ResponseMessageDTO.class)
    @PostMapping("/updateUserDTO")
    @PreAuthorize("isAuthenticated()")
    public ResponseMessageDTO updateUserDTO(@RequestBody UserDTO user) {
        userService.updateUser(user);
        return ResponseMessageDTO.get("msg_general_success");
    }

    @Override
    @ApiOperation(value = "Save entity", response = ResponseMessageDTO.class)
    @PostMapping("/saveDTO")
    public ResponseMessageDTO saveDTO(@RequestBody UserDTO entityDTO) {
        final ModelMapper modelMapper = new ModelMapper();
        User entity = convertDTOtoEntity(entityDTO);
        Set<Authority> authorities = new HashSet<>();
        entity.setAuthorities(authorities);

        if (entityDTO.getAppModules() != null && !entityDTO.getAppModules().isEmpty()) {
            for (AppModuleDTO appModuleDTO : entityDTO.getAppModules()) {
                AppModule appModule = modelMapper.map(appModuleDTO, AppModule.class);
                if (appModuleDTO.getSelectedAuthorities() != null && !appModuleDTO.getSelectedAuthorities().isEmpty()) {
                    for (AuthorityDTO authorityDTO : appModuleDTO.getSelectedAuthorities()) {
                        Authority authority = modelMapper.map(authorityDTO, Authority.class);
                        authority.setAppModule(appModule);
                        authorities.add(authority);
                    }
                }
            }
        }

        if (entity.getId() == null) {
            this.getService().save(entity);
        } else {
            this.getService().update(entity);
        }
        return ResponseMessageDTO.get("msg_general_success");
    }

    @Override
    public UserService getService() {
        return userService;
    }
}