package com.myservice.auth.service.impl;

import com.myservice.auth.model.AppModule;
import com.myservice.auth.repository.AppModuleRepository;
import com.myservice.auth.service.AppModuleService;
import com.myservice.common.dto.auth.AppModuleDTO;
import com.myservice.common.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AppModuleServiceImpl extends MyService<Long, AppModule, AppModuleDTO> implements AppModuleService {

    @Autowired
    AppModuleRepository appModuleRepository;

    @Override
    public AppModuleRepository getRepository() {
        return appModuleRepository;
    }
}
