package com.myservice.auth.repository;

import com.myservice.auth.model.Company;
import com.myservice.common.domain.StatusEnum;
import com.myservice.common.dto.auth.CompanyDTO;
import com.myservice.common.repository.IRepository;

import java.util.List;

public interface CompanyRepository extends IRepository<Long, Company, CompanyDTO> {

    List<Company> findTop20ByNameContainingIgnoreCaseAndStatus(String name, StatusEnum status);

    Company findByCode(String code);
}