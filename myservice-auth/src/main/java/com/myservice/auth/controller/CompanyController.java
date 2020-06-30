package com.myservice.auth.controller;

import com.myservice.auth.model.Company;
import com.myservice.auth.service.CompanyService;
import com.myservice.common.controller.MyController;
import com.myservice.common.domain.Authorities;
import com.myservice.common.dto.auth.CompanyDTO;
import com.myservice.common.service.IService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@Api(value = "AUTH API - Company")
@PreAuthorize(Authorities.HAS_AUTHORITY_AUTH_ADMIN)
public class CompanyController extends MyController<Long, Company, CompanyDTO> {

    @Autowired
    CompanyService companyService;

    @Override
    public IService getService() {
        return companyService;
    }
}