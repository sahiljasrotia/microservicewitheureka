package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class SecurityConfig  {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/login",        // allow public login
//                                "/eureka/**",    // allow Eureka server access
//                                "/actuator/**"   // allow health checks
//                        ).permitAll()
//                        .anyRequest().authenticated()  // all other routes require JWT auth
//                );
//
//        return http.build();
//    }
}
