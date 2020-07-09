package com.myservice.auth.service.impl;

import com.myservice.auth.model.*;
import com.myservice.auth.repository.CompanyRepository;
import com.myservice.auth.repository.ContractRepository;
import com.myservice.auth.repository.UserRepository;
import com.myservice.auth.service.MailService;
import com.myservice.auth.service.UserService;
import com.myservice.common.domain.StatusEnum;
import com.myservice.common.dto.auth.AppModuleDTO;
import com.myservice.common.dto.auth.CompanyDTO;
import com.myservice.common.dto.auth.UserDTO;
import com.myservice.common.exceptions.BusinessException;
import com.myservice.common.exceptions.MessageException;
import com.myservice.common.service.MyService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl extends MyService<Long, User, UserDTO> implements UserService {

    @Autowired
    MailService mailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ContractRepository contractRepository;

    public UserDTO loadUserInfoBy(String code, String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByCodeCompanyAndUsernameAndStatus(code, username, StatusEnum.ACTIVE);
        if (userOptional.isPresent()) {
            return parseDTO(userOptional.get());
        } else {
            throw new  UsernameNotFoundException("User " + username + " Not found");
        }
    }

    @Override
    protected void prepareSave(User entity) {
        String newPassword = "123456";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        entity.setPassword(passwordEncoder.encode(newPassword));
        entity.setCompany(companyRepository.getOne(entity.getCompany().getId()));
        Optional<User> userBD = getRepository().findByCodeCompanyAndUsername(entity.getCompany().getCode(), entity.getUsername());
        if (userBD.isPresent()) {
            throw new BusinessException(MessageException.MSG_GENERAL_VALIDATE.getMessage(), MessageException.MSG_USERNAME_EXIST.getMessage());
        }
        validateCountUser(entity);
        sendMailWelcome(entity, newPassword);
        super.prepareSave(entity);
    }

    private void validateCountUser(User entity) {
        List<Contract> contracts = contractRepository.findContractByCompanyIdAndStatusAndValidate(StatusEnum.ACTIVE,entity.getCompany().getId(), new Date());
        Integer maxUser = 0;
        for(Contract contract : contracts){
            if(contract.getCountUser() > maxUser){
                maxUser =contract.getCountUser();
            }
        }
        Long count = userRepository.countUserByStatusAndCompany(StatusEnum.ACTIVE,entity.getCompany().getId());
        if(count >= maxUser && StatusEnum.ACTIVE.equals(entity.getStatus())){
            throw new BusinessException(MessageException.MSG_GENERAL_VALIDATE.getMessage(), MessageException.MSG_CONTRACT_LIMIT_USER.getMessage());
        }

    }

    private void sendMailWelcome(User entity, String newPassword) {
        try {
            mailService.sendEmailHtml(fillHtmlWelcome(entity, newPassword), "MyServices - Bem vinda(o)", entity.getEmail());
        } catch (Exception e) {
        }
    }

    @Override
    protected void prepareUpdate(User entity) {
        Optional<User> userBD = getRepository().findByCodeCompanyAndUsername(entity.getCompany().getCode(), entity.getUsername());
        if (userBD.isPresent() && entity.getId() != userBD.get().getId()) {
            throw new BusinessException(MessageException.MSG_GENERAL_VALIDATE.getMessage(), MessageException.MSG_USERNAME_EXIST.getMessage());
        }
        validateCountUser(entity);
        super.prepareUpdate(entity);
    }

    @Override
    public void forgotPassword(String codeCompany, String username) {
        UserDTO userDTO = userRepository.findByCodeCompanyAndUsernameAndStatus(codeCompany, username, StatusEnum.ACTIVE)
                .map(this::parseDTO)
                .orElseThrow(() -> new BusinessException(MessageException.ERROR_SEND_MAIL.getMessage(), MessageException.USER_NOT_FOUND.getMessage()));
        try {
            String newPassword = "123456";
            mailService.sendEmailHtml(fillHtmlForgotPassword(userDTO, newPassword), "Recuperar Senha", userDTO.getEmail());
            User user = userRepository.findByCodeCompanyAndUsernameAndStatus(codeCompany, username, StatusEnum.ACTIVE).get();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } catch (Exception e) {
            throw new BusinessException(MessageException.ERROR_SEND_MAIL.getMessage(), MessageException.ERROR_SEND_MAIL.getMessage());
        }
    }

    @Override
    public void changePassword(UserDTO userDTO) {
        if (Strings.isBlank(userDTO.getPassword()) || Strings.isBlank(userDTO.getNewPassword())) {
            throw new BusinessException(MessageException.MSG_REQUIRED_FIELDS.getMessage(), MessageException.MSG_REQUIRED_FIELDS.getMessage());
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String newPasswordEncoded = passwordEncoder.encode(userDTO.getNewPassword());
        Optional<User> userOptional = userRepository.findById(userDTO.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                throw new BusinessException(MessageException.MSG_INVALID_PASSWORD.getMessage(), MessageException.MSG_INVALID_PASSWORD.getMessage());
            }
            user.setPassword(newPasswordEncoded);
            userRepository.save(user);
        } else {
            throw new BusinessException(MessageException.ERROR_SEND_MAIL.getMessage(), MessageException.USER_NOT_FOUND.getMessage());
        }

    }

    @Override
    public void updateUser(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(userDTO.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setUsername(userDTO.getUsername());
            userRepository.save(user);
        } else {
            throw new BusinessException(MessageException.ERROR_SEND_MAIL.getMessage(), MessageException.USER_NOT_FOUND.getMessage());
        }
    }


    private String fillHtmlForgotPassword(UserDTO user, String newPassword) throws IOException {
        InputStream resource = new ClassPathResource("forgot-password.html").getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
        String template = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        template = template.replaceAll("@username", user.getName()).replaceAll("@password", newPassword);
        return template;
    }

    private String fillHtmlWelcome(User user, String newPassword) throws IOException {
        InputStream resource = new ClassPathResource("welcome.html").getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
        String template = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        template = template.replaceAll("@username", user.getName()).replaceAll("@password", newPassword);
        return template;
    }

    private UserDTO parseDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAuthorities(getAuthoritiesByContract(user));
        userDTO.setAppModules(user.getAuthorities().stream().map(Authority::getAppModule).map(this::parseModuleDTO).collect(Collectors.toList()));
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(user.getCompany().getId());
        companyDTO.setClient(user.getCompany().getClient());
        companyDTO.setFiscalNumber(user.getCompany().getFiscalNumber());
        companyDTO.setName(user.getCompany().getName());
        userDTO.setCompany(companyDTO);
        return userDTO;
    }

    private List<String> getAuthoritiesByContract(User user) {
        HashSet<String> modules = getContractModules(user);
        List<String> authorities = new ArrayList<>();
        for (Authority authority : user.getAuthorities()) {
            if (modules.contains(authority.getAppModule().getName())) {
                authorities.add(authority.getName());
            }
        }
        return authorities;
    }

    private HashSet<String> getContractModules(User user) {
        Company company = user.getCompany();
        List<Contract> contracts = contractRepository.findContractByCompanyIdAndStatusAndValidate(StatusEnum.ACTIVE,company.getId(), new Date());
        if (contracts == null || contracts.isEmpty()) {
            throw new BusinessException(MessageException.MSG_GENERAL_VALIDATE.getMessage(), MessageException.MSG_CONTRACT_EXPIRED.getMessage());
        }
        HashSet<String> modules = new HashSet<>();
        for (Contract contract : contracts) {
            List<String> moduleContract = contract.getAppModules().stream().map(AppModule::getName).collect(Collectors.toList());
            modules.addAll(moduleContract);
        }
        return modules;
    }

    private AppModuleDTO parseModuleDTO(AppModule appModule) {
        AppModuleDTO appModuleDTO = new AppModuleDTO();
        if (appModule != null) {
            appModuleDTO.setId(appModule.getId());
            appModuleDTO.setName(appModule.getName());
            appModuleDTO.setDescription(appModule.getDescription());
            appModuleDTO.setWebUrl(appModule.getWebUrl());
        }
        return appModuleDTO;
    }

    @Override
    public UserRepository getRepository() {
        return userRepository;
    }
}
