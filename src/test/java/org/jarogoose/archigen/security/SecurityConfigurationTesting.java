package org.jarogoose.archigen.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ActiveProfiles("test")
public class SecurityConfigurationTesting {

    @Bean
    @Profile("test")
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.authorizeHttpRequests()
                .requestMatchers("/**")
                .permitAll();
        return http.build();
    }
}
