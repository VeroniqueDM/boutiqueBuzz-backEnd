package com.api.boutiquebuzz.config;


import com.api.boutiquebuzz.domain.entities.RegistrationSource;
import com.api.boutiquebuzz.domain.entities.UserEntity;
import com.api.boutiquebuzz.domain.entities.UserRole;
import com.api.boutiquebuzz.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        if ("github".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = principal.getAttributes();
            String username = attributes.getOrDefault("login","").toString();
            String email = attributes.getOrDefault("email", "").toString();
            String name = attributes.getOrDefault("name", "").toString();
            userService.findByEmail(email)
                    .ifPresentOrElse(user -> {
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                attributes, "id");
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    }, () -> {
                        UserEntity userEntity = new UserEntity();
                        userEntity.setRole(UserRole.ROLE_USER);
                        userEntity.setEmail(email);
                        userEntity.setName(name);
                        userEntity.setUsername(username);
                        userEntity.setSource(RegistrationSource.GITHUB);
                        userService.save(userEntity);
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
                                attributes, "id");
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    });
        } else   if ("google".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = principal.getAttributes();
            String username = attributes.getOrDefault("given_name","").toString();
            String email = attributes.getOrDefault("email", "").toString();
            String name = attributes.getOrDefault("name", "").toString();
            userService.findByEmail(email)
                    .ifPresentOrElse(user -> {
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                attributes, "sub");
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    }, () -> {
                        UserEntity userEntity = new UserEntity();
                        userEntity.setRole(UserRole.ROLE_USER);
                        userEntity.setEmail(email);
                        userEntity.setName(name);
                        userEntity.setUsername(username);
                        userEntity.setSource(RegistrationSource.GITHUB);
                        userService.save(userEntity);
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
                                attributes, "id");
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    });
        }
        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(frontendUrl);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
