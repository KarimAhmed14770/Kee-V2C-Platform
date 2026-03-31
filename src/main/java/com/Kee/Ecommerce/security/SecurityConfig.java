package com.Kee.Ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Disable CSRF (Cross-Site Request Forgery)
        // We disable this because we are building a stateless REST API,
        // and CSRF is primarily for stateful session-based browser apps.

        http.csrf(csrf -> csrf.disable());

        //providing http basic security
        http.httpBasic(Customizer.withDefaults());

        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/api/auth/**").permitAll()// Make registration public
                        .requestMatchers("/api/test/securityDBhandShake").hasRole("CUSTOMER")
                        .anyRequest().authenticated()


        );




        return http.build();
    }
}
