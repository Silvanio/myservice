package com.myservice.auth.service.impl;

import com.myservice.auth.model.Authority;
import com.myservice.auth.model.Company;
import com.myservice.auth.model.Contract;
import com.myservice.auth.model.User;
import com.myservice.auth.model.dto.UserCompanyDTO;
import com.myservice.auth.repository.AuthorityRepository;
import com.myservice.auth.repository.CompanyRepository;
import com.myservice.auth.repository.ContractRepository;
import com.myservice.auth.repository.UserRepository;
import com.myservice.auth.service.ContractService;
import com.myservice.common.dto.auth.AppModuleDTO;
import com.myservice.common.dto.auth.AuthorityDTO;
import com.myservice.common.dto.auth.ContractDTO;
import com.myservice.common.exceptions.BusinessException;
import com.myservice.common.exceptions.MessageException;
import com.myservice.common.service.MyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContractServiceImpl extends MyService<Long, Contract, ContractDTO> implements ContractService {

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Override
    protected void validate(Contract entity) {
        super.validate(entity);
        if (entity.getInitialDate().after(entity.getFinalDate())) {
            throw new BusinessException(MessageException.MSG_GENERAL_VALIDATE.getMessage(), MessageException.MSG_PERIOD_DATE_INVALID.getMessage());
        }
    }

    @Override
    public List<AppModuleDTO> findModules(UserCompanyDTO userCompanyDTO) {
        LinkedHashSet<AppModuleDTO> modules = fillModules(userCompanyDTO);
        fillAuthorities(userCompanyDTO, modules);
        List<AppModuleDTO> moduleDTOList = new ArrayList<>();
        moduleDTOList.addAll(modules);
        return moduleDTOList;
    }

    private void fillAuthorities(UserCompanyDTO userCompanyDTO, LinkedHashSet<AppModuleDTO> modules) {
        final ModelMapper modelMapper = new ModelMapper();
        User user = null;
        if (userCompanyDTO.getIdUser() != null) {
            user = userRepository.findById(userCompanyDTO.getIdUser()).get();
        }
        Company company = companyRepository.getOne(userCompanyDTO.getIdCompany());
        for (AppModuleDTO module : modules) {
            boolean isClient = company.getClient() == null ? false : company.getClient();
            List<Authority> authorities = authorityRepository.findByClientAndModuleId(isClient,module.getId());
            List<AuthorityDTO> authoritiesDTOList = authorities
                    .stream()
                    .map(e -> modelMapper.map(e, AuthorityDTO.class))
                    .collect(Collectors.toList());

            module.setAuthorities(authoritiesDTOList);
            module.setSelectedAuthorities(new ArrayList<>());

            if (user != null && user.getAuthorities() != null) {
                for (Authority authority : user.getAuthorities()) {
                    if (authority.getAppModule().getId() == module.getId() && authorities.contains(authority)) {
                        module.getSelectedAuthorities().add(modelMapper.map(authority, AuthorityDTO.class));
                    }
                }
            }

        }
    }

    private LinkedHashSet<AppModuleDTO> fillModules(UserCompanyDTO userCompanyDTO) {
        final ModelMapper modelMapper = new ModelMapper();
        List<Contract> contracts = getRepository().findByCompanyId(userCompanyDTO.getIdCompany());
        LinkedHashSet<AppModuleDTO> modules = new LinkedHashSet<AppModuleDTO>();
        for (Contract contract : contracts) {
            List<AppModuleDTO> moduleDTOList = contract.getAppModules()
                    .stream()
                    .map(e -> modelMapper.map(e, AppModuleDTO.class))
                    .collect(Collectors.toList());

            modules.addAll(moduleDTOList);

        }
        return modules;
    }

    @Override
    public ContractRepository getRepository() {
        return contractRepository;
    }


}
