package org.jarogoose.archigen.web.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> {
            csrf.ignoringRequestMatchers("/actuator/**", "/register");
            csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        });

        http.addFilter(CorsFilter());

        http.authorizeHttpRequests()
                .requestMatchers("/actuator/**", "/register", "/login").permitAll()
                .requestMatchers("/archigen-ui/**").authenticated();

        http.formLogin();
        http.httpBasic();
        return http.build();
    }

    @Bean
    public CorsFilter CorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("https://localhost:4200", "https://localhost:9100"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(source);
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails admin = User
                .withUsername("user")
                .password("password")
                .authorities("general")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    @Profile("!local")
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile("local")
    PasswordEncoder localPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
