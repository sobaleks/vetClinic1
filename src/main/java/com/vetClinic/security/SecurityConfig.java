package com.vetClinic.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailService customUserDetailService;

    @Autowired
    public SecurityConfig(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/owner/myPets/**").hasAnyRole("ADMIN", "USER", "DOCTOR")
                .requestMatchers(HttpMethod.GET, "/owner/consult").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/owner/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/owner/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/owner/recCons/**").hasAnyRole("ADMIN","USER")
                .requestMatchers(HttpMethod.PUT, "/owner/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/owner/**").hasAnyRole("ADMIN", "USER")

                .requestMatchers(HttpMethod.GET, "/doctor/**").hasAnyRole("ADMIN", "DOCTOR")
                .requestMatchers(HttpMethod.GET, "/doctor/search/**").hasAnyRole("ADMIN", "DOCTOR", "USER")
                .requestMatchers(HttpMethod.POST, "/doctor/createVK").hasAnyRole("ADMIN", "DOCTOR")
                .requestMatchers(HttpMethod.POST, "/doctor/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/doctor/**").hasAnyRole("ADMIN", "DOCTOR")
                .requestMatchers(HttpMethod.DELETE, "/doctor/**").hasAnyRole("ADMIN", "DOCTOR")

                .requestMatchers(HttpMethod.GET, "/pet/**").hasAnyRole("ADMIN", "DOCTOR", "USER")
                .requestMatchers(HttpMethod.POST, "/pet/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/pet/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/pet/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/card/**").hasAnyRole("ADMIN", "DOCTOR", "USER")
                .requestMatchers(HttpMethod.POST, "/card/**").hasAnyRole("ADMIN", "DOCTOR")
                .requestMatchers(HttpMethod.PUT, "/card/**").hasAnyRole("ADMIN", "DOCTOR")
                .requestMatchers(HttpMethod.DELETE, "/card/**").hasAnyRole("ADMIN", "DOCTOR")

                .requestMatchers(HttpMethod.GET, "/appointment/**").hasAnyRole("ADMIN", "USER", "DOCTOR")
                .requestMatchers(HttpMethod.GET, "/appointment/owner/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/appointment/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/appointment/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/appointment/**").hasAnyRole("ADMIN", "USER")

                .requestMatchers(HttpMethod.GET, "/schedule/**").hasAnyRole("ADMIN", "DOCTOR")
                .requestMatchers(HttpMethod.POST, "/schedule/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/schedule/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/schedule/**").hasAnyRole("ADMIN")

                .anyRequest().authenticated()
                .and()
                .userDetailsService(customUserDetailService)
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

