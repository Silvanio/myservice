package com.myservice.config.myserviceconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class MyServiceConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyServiceConfigServerApplication.class, args);
    }

}
