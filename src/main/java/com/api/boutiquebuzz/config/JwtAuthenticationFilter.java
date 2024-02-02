package com.api.boutiquebuzz.config;


import com.api.boutiquebuzz.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.beans.Transient;
import java.io.IOException;
import java.security.Security;

import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        System.out.println("Filter started for " + request.getMethod() + " " + request.getServletPath());
        System.out.println("Servlet path:" + request.getServletPath());
        System.out.println("Request header:" + request.getHeader("Authorization"));
        if (request.getServletPath().contains("/api/v1/auth/authenticate") || request.getServletPath().contains("/api/v1/auth/register")) {

//        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }


        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("Filter executing for " + request.getMethod() + " " + request.getServletPath());
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
//        var nameUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userEmail != null && (SecurityContextHolder.getContext().getAuthentication() == null
//                || nameUser.equals("anonymousUser")
        )) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            System.out.println("Filter executed for " + request.getMethod() + " " + request.getServletPath());

            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("SecurityContextHolder at the moment : " + SecurityContextHolder.getContext());
//                SecurityContextHolderStrategy currentMode = SecurityContextHolder.getContextHolderStrategy();
//                System.out.println("Strategy: " + currentMode);

//                return;

            }
        }
        SecurityContextHolder.getContext();
        filterChain.doFilter(request, response);
        System.out.println("Filter  executed2!");
        System.out.println("SecConHolder (6): " + SecurityContextHolder.getContext());

    }
}
