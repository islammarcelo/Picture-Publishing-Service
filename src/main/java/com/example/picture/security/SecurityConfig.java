package com.example.picture.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.picture.security.MyCustomDsl.customDsl;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.csrf().disable();
        http.authorizeRequests().requestMatchers(POST,"/login/**").permitAll();
        http.authorizeRequests().requestMatchers(POST,"/api/register").permitAll();
        http.authorizeRequests().requestMatchers(POST,"/api/add/roleToUser").permitAll();
        http.authorizeRequests().requestMatchers(GET,"/api/images/accepted").permitAll();
        http.authorizeRequests().requestMatchers(POST,"/api/image/upload").hasAnyAuthority("user");
        http.authorizeRequests().requestMatchers(GET,"/api/images/uploaded").hasAnyAuthority("admin");
        http.authorizeRequests().requestMatchers(PUT,"/api/image/update/**").hasAnyAuthority("admin");
        http.authorizeRequests().requestMatchers(GET,"/api/image/info/**").hasAnyAuthority("admin");
        http.authorizeRequests().requestMatchers(GET,"/api/image/view/**").hasAnyAuthority("admin");
        http.authorizeRequests().anyRequest().authenticated();
        http.apply(customDsl());

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }
}
