package com.myservice.auth.service;

import com.myservice.auth.model.Contract;
import com.myservice.auth.model.dto.UserCompanyDTO;
import com.myservice.common.dto.auth.AppModuleDTO;
import com.myservice.common.dto.auth.ContractDTO;
import com.myservice.common.service.IService;

import java.util.List;

public interface ContractService extends IService<Long, Contract, ContractDTO> {

    List<AppModuleDTO> findModules(UserCompanyDTO userCompanyDTO);
}
