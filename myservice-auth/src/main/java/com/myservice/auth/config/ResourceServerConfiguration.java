package com.myservice.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.client.resource-ids}")
    private String RESOURCE_ID;

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
                .antMatchers("/createUserMyMarket/**").permitAll()
                .antMatchers("/forgotPassword/**").permitAll()
                //Permissão para @EnableAdminServer
                .antMatchers("/instances/*","/actuator/**","/assets/**","/management/**").permitAll()
                //Permissão para @EnableSwagger2
                .antMatchers("/swagger*", "/v2/**","/webjars/**","/swagger-resources/**","/api/**").permitAll()
                .anyRequest()
                .authenticated();
    }
}