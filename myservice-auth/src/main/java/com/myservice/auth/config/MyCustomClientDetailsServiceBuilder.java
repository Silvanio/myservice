package com.myservice.auth.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

public class MyCustomClientDetailsServiceBuilder extends ClientDetailsServiceBuilder<JdbcClientDetailsServiceBuilder> {

    private Set<ClientDetails> clientDetails = new HashSet();
    private DataSource dataSource;
    private PasswordEncoder passwordEncoder;

    public MyCustomClientDetailsServiceBuilder() {
    }

    public MyCustomClientDetailsServiceBuilder dataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public MyCustomClientDetailsServiceBuilder passwordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    protected void addClient(String clientId, ClientDetails value) {
        this.clientDetails.add(value);
    }

    @Override
    protected ClientDetailsService performBuild() {
        Assert.state(dataSource != null, "You need to provide a DataSource");
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        if (passwordEncoder != null) {
            clientDetailsService.setPasswordEncoder(passwordEncoder);
        }
        for (ClientDetails client : clientDetails) {
            try {
                clientDetailsService.updateClientDetails(client);
            } catch (NoSuchClientException e) {
                clientDetailsService.addClientDetails(client);
            }
        }
        return clientDetailsService;
    }
}
