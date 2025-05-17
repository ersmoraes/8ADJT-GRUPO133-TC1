package br.com.fiap.techChallenge.restaurante_api.config;

import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@Profile("dev")
public class LocalSecurityConfig {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}admin")
                .roles(UserType.PROPRIETARIO.name())
                .and()
                .withUser("user")
                .password("{noop}user")
                .roles(UserType.CLIENTE.name());
    }
}
