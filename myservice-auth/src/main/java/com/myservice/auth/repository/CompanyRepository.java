package com.myservice.auth.repository;

import com.myservice.auth.model.Company;
import com.myservice.common.dto.auth.CompanyDTO;
import com.myservice.common.repository.IRepository;

public interface CompanyRepository extends IRepository<Long, Company, CompanyDTO> {

}