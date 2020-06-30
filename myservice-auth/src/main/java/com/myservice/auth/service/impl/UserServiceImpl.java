package com.myservice.auth.service.impl;

import com.myservice.auth.model.AppModule;
import com.myservice.auth.model.Authority;
import com.myservice.auth.model.User;
import com.myservice.common.dto.auth.AppModuleDTO;
import com.myservice.common.dto.auth.CompanyDTO;
import com.myservice.common.dto.auth.UserDTO;
import com.myservice.auth.repository.UserRepository;
import com.myservice.auth.service.MailService;
import com.myservice.auth.service.UserService;
import com.myservice.common.exceptions.BusinessException;
import com.myservice.common.exceptions.MessageException;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    MailService mailService;

    @Autowired
    UserRepository userRepository;

    public UserDTO loadUserInfoBy(String code, String username) throws UsernameNotFoundException {
        return userRepository.findByCodeCompanyAndUsername(code, username)
                .map(this::parseDTO)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " Not found"));
    }

    @Override
    public void forgotPassword(String codeCompany, String username) {
        UserDTO userDTO = userRepository.findByCodeCompanyAndUsername(codeCompany, username)
                .map(this::parseDTO)
                .orElseThrow(() -> new BusinessException(MessageException.ERROR_SEND_MAIL.getMessage(), MessageException.USER_NOT_FOUND.getMessage()));
        try {
            String newPassword = "123456";
            mailService.sendEmailHtml(fillHtmlForgotPassword(userDTO, newPassword), "Recuperar Senha", userDTO.getEmail());
            User user = userRepository.findByCodeCompanyAndUsername(codeCompany, username).get();
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
    public void update(UserDTO userDTO) {
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

    private UserDTO parseDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAuthorities(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toList()));
        userDTO.setAppModules(user.getAuthorities().stream().map(Authority::getAppModule).map(this::parseModuleDTO).collect(Collectors.toList()));
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setClient(user.getCompany().getClient());
        companyDTO.setFiscalNumber(user.getCompany().getFiscalNumber());
        companyDTO.setName(user.getCompany().getName());
        userDTO.setCompany(companyDTO);
        return userDTO;
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
}
