package com.myservice.auth.controller;

import com.myservice.auth.model.domain.Authorities;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TesteController {


    @RequestMapping("/outro")
    public String getCurrentLoggedInUser() {
        return "Deu Certo";
    }

    @RequestMapping("/testar")
    @PreAuthorize(Authorities.HAS_AUTHORITY_AUTH_ADMIN)
    public String testar() {
        return "Permissão para AUTH_ADMIN";
    }
}