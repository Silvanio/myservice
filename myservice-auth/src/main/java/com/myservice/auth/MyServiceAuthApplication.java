package com.myservice.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
public class MyServiceAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyServiceAuthApplication.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.myservice.auth.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "MyServices API - Acessos",
                "API rensponsável por gerenciar as funcionalidades MyServices - Acessos.",
                "API Version v01_20062020",
                "Terms of service",
                new Contact("Silvânio Júnior", "www.linkedin.com/in/silvaniojunior", "jrsilvanio@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

}
