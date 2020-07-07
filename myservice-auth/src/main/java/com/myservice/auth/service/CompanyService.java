package com.myservice.auth.service;

import com.myservice.auth.model.Company;
import com.myservice.common.dto.auth.CompanyDTO;
import com.myservice.common.service.IService;

import java.util.List;


public interface CompanyService extends IService<Long, Company, CompanyDTO> {

    List<Company> findAllByName(String name);
}
