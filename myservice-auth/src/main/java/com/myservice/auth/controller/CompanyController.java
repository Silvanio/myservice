package com.myservice.auth.controller;

import com.myservice.auth.model.Company;
import com.myservice.auth.service.CompanyService;
import com.myservice.common.controller.MyController;
import com.myservice.common.dto.auth.CompanyDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/company")
@Api(value = "AUTH API - Company")
public class CompanyController extends MyController<Long, Company, CompanyDTO> {

    @Autowired
    CompanyService companyService;

    @Override
    public void permitAuthorities(List<String> authorities) {
        authorities.add("AUTH_ADMIN");
    }

    @ApiOperation(value = "List by Name", response = List.class)
    @PostMapping("/findAllByName")
    public Collection<CompanyDTO> findAllByName(@RequestBody String name) {
        List<Company> companies = getService().findAllByName(name);
        return convertListEntityToDTO(companies);
    }

    @Override
    public CompanyService getService() {
        return companyService;
    }
}