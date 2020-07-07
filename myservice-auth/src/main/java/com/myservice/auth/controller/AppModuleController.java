package com.myservice.auth.controller;

import com.myservice.auth.model.AppModule;
import com.myservice.auth.service.AppModuleService;
import com.myservice.common.controller.MyController;
import com.myservice.common.dto.auth.AppModuleDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appModule")
@Api(value = "AUTH API - AppModule")
public class AppModuleController extends MyController<Long, AppModule, AppModuleDTO> {

    @Autowired
    AppModuleService appModuleService;

    @Override
    public void permitAuthorities(List<String> authorities) {
        authorities.add("AUTH_ADMIN");
    }

    @Override
    public AppModuleService getService() {
        return appModuleService;
    }

}