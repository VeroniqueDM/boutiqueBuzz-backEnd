package com.api.boutiquebuzz.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
//    private final JwtAuthenticationFilter jwtAuthFilter;


//    public BeanConfiguration(JwtAuthenticationFilter jwtAuthFilter) {
//        this.jwtAuthFilter = jwtAuthFilter;
//    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    @Bean
//    public FilterRegistrationBean<JwtAuthenticationFilter> customJwtAuthenticationFilter() {
//        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(jwtAuthFilter);
//        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1); // Adjust the order as needed
//        return registrationBean;
//    }
}
