//package com.api.boutiquebuzz.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration {
//    @Bean
//    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorise -> authorise
//                        .requestMatchers(HttpMethod.GET).permitAll()
//                        .requestMatchers(HttpMethod.POST).permitAll()
//                        .requestMatchers(HttpMethod.PUT).fullyAuthenticated()
//                        .requestMatchers(HttpMethod.DELETE).fullyAuthenticated()
//                        .anyRequest().denyAll())
//                .sessionManagement(sessionManagement -> sessionManagement
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .oauth2ResourceServer(resource -> resource
//                        .jwt(Customizer.withDefaults()))
//                .cors(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable);
//        return http.build();
//    }
//}