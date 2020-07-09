package com.myservice.auth.service;

import com.myservice.auth.model.AppModule;
import com.myservice.auth.model.Contract;
import com.myservice.common.dto.auth.AppModuleDTO;
import com.myservice.common.dto.auth.ContractDTO;
import com.myservice.common.service.IService;

import java.util.List;

public interface AppModuleService extends IService<Long, AppModule, AppModuleDTO> {

}
