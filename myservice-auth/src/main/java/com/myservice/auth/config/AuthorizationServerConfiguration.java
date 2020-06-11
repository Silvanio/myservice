package com.myservice.auth.config;

import javax.sql.DataSource;

import com.myservice.auth.model.domain.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private static PasswordEncoder encoder;

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.authorized-grant-types}")
    private String[] authorizedGrantTypes;

    @Value("${security.oauth2.client.resource-ids}")
    private String resourceIds;

    @Value("${security.oauth2.client.scope}")
    private String[] scopes;

    @Value("${security.oauth2.client.client-secret}")
    private String secret;

    @Value("${security.oauth2.client.access-token-validity-seconds}")
    private Integer accessTokenValiditySeconds;

    @Autowired
    DataSource dataSource;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public JdbcTokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    protected AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints.authenticationManager(this.authenticationManager).tokenStore(tokenStore());

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        MyCustomClientDetailsServiceBuilder clientDetailsServiceBuilder = new MyCustomClientDetailsServiceBuilder();

        clientDetailsServiceBuilder.dataSource(dataSource)
                .withClient(clientId)
                .secret(encoder.encode(secret))
                .authorizedGrantTypes(authorizedGrantTypes)
                .authorities(Authorities.names())
                .resourceIds(resourceIds)
                .scopes(scopes)
                .accessTokenValiditySeconds(accessTokenValiditySeconds);

        clients.setBuilder(clientDetailsServiceBuilder);

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        if (encoder == null) {
            encoder = new BCryptPasswordEncoder();
        }
        return encoder;
    }
}