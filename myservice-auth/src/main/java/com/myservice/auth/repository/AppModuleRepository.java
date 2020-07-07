package com.myservice.auth.repository;

import com.myservice.auth.model.AppModule;
import com.myservice.common.dto.auth.AppModuleDTO;
import com.myservice.common.repository.IRepository;


public interface AppModuleRepository extends IRepository<Long, AppModule, AppModuleDTO> {


}