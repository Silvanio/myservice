package com.myservice.auth.service.impl;

import com.myservice.auth.model.Company;
import com.myservice.auth.repository.CompanyRepository;
import com.myservice.auth.service.CompanyService;
import com.myservice.common.domain.StatusEnum;
import com.myservice.common.dto.auth.CompanyDTO;
import com.myservice.common.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl extends MyService<Long, Company, CompanyDTO> implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public List<Company> findAllByName(String name) {
        return companyRepository.findTop20ByNameContainingIgnoreCaseAndStatus(name, StatusEnum.ACTIVE);
    }

    @Override
    public CompanyRepository getRepository() {
        return companyRepository;
    }
}
