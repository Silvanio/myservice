package com.myservice.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private final static String RESOURCE_ID = "resources";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.requestMatchers()
                .antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers("/forgotPassword/**").permitAll()
                //Permissão para @EnableAdminServer
                .antMatchers("/instances/*","/actuator/**","/assets/**","/management/**").permitAll()
                //Permissão para @EnableSwagger2
                .antMatchers("/swagger*", "/v2/**","/webjars/**","/swagger-resources/**","/api/**").permitAll()
                .anyRequest()
                .authenticated();
    }
}