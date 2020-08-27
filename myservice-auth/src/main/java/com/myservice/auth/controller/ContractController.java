package com.myservice.auth.controller;

import com.myservice.auth.model.dto.UserCompanyDTO;
import com.myservice.auth.service.ContractService;
import com.myservice.auth.model.Contract;
import com.myservice.common.controller.MyController;
import com.myservice.common.dto.auth.AppModuleDTO;
import com.myservice.common.dto.auth.ContractDTO;
import com.myservice.common.dto.pagination.PageDTO;
import com.myservice.common.dto.pagination.PageableDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contract")
@Api(value = "AUTH API - Contract")
public class ContractController extends MyController<Long, Contract, ContractDTO> {

    @Autowired
    ContractService contractService;

    @Override
    public void permitAuthorities(List<String> authorities) {
        authorities.add("AUTH_ADMIN");
    }

    @ApiOperation(value = "List Company Modules", response = List.class)
    @PostMapping("/findModules")
    @PreAuthorize("isAuthenticated()")
    public List<AppModuleDTO> findModules(@RequestBody UserCompanyDTO userCompanyDTO) {
        return getService().findModules(userCompanyDTO);
    }

    @ApiOperation(value = "List All Pageable", response = PageDTO.class)
    @PostMapping("/findMyContracts")
    @PreAuthorize("hasAuthority('AUTH_CLIENT_ADMIN')")
    public PageDTO<ContractDTO> findMyContracts(@RequestBody PageableDTO<Contract, ContractDTO> pageableDTO) {
        if(pageableDTO.getEntity().getCompany().getId() == null){
            return null;
        }
        return pageToDTO(getService().findAll(pageableDTO));
    }


    @Override
    public ContractService getService() {
        return contractService;
    }

}