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
                .requestMatchers(HttpMethod.GET,"/owner/**").hasAnyRole("USER", "ADMIN", "DOCTOR")
                .requestMatchers(HttpMethod.POST,"/owner/**").permitAll()
                .requestMatchers(HttpMethod.PUT,"/owner/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/owner/**").hasAnyRole( "ADMIN")


                .requestMatchers(HttpMethod.GET,"/doctor/**").hasAnyRole("USER", "ADMIN", "DOCTOR")
                .requestMatchers(HttpMethod.POST,"/doctor/**").hasAnyRole("DOCTOR", "ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT,"/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/doctor/**").hasAnyRole( "ADMIN")

                .requestMatchers(HttpMethod.GET,"/pet/**").hasAnyRole("USER", "ADMIN", "DOCTOR")
                .requestMatchers(HttpMethod.POST,"/pet/**").hasAnyRole("USER", "ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT,"/pet/**").hasAnyRole("USER", "ADMIN", "DOCTOR")
                .requestMatchers(HttpMethod.DELETE,"/pet/**").hasAnyRole(  "ADMIN")

                .requestMatchers(HttpMethod.GET,"/card/**").hasAnyRole("USER", "ADMIN", "DOCTOR")
                .requestMatchers(HttpMethod.POST,"/card/**").hasAnyRole("DOCTOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT,"/card/**").hasAnyRole("DOCTOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/card/**").hasAnyRole( "ADMIN")

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

