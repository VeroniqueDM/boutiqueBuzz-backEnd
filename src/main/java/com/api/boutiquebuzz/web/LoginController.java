package com.api.boutiquebuzz.web;


import com.api.boutiquebuzz.domain.dtos.user.LoginUserDto;
import com.api.boutiquebuzz.services.ApplicationUserDetailsService;
import com.api.boutiquebuzz.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class LoginController {
    @Autowired
    private final UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final ApplicationUserDetailsService applicationUserDetailsService;
    //    private EmailService emailService;
    @Autowired
    public LoginController(UserService userService, ApplicationUserDetailsService applicationUserDetailsService) {
        this.userService = userService;
        this.applicationUserDetailsService = applicationUserDetailsService;
    }
    @GetMapping("/login")
    public String getLogin() {
        return "auth-login";
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        String username = loginUserDto.getUsername();
        String password = loginUserDto.getPassword();
        UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(username);

        // Perform authentication logic using Spring Security or custom authentication logic

        if (passwordMatches(userDetails, password)) {
            return ResponseEntity.ok("{\"message\": \"Login successful\", \"username\": \"" + username + "\"}");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"Login failed\"}");
        }
    }
    private boolean passwordMatches(UserDetails userDetails, String providedPassword) {
        // Check if the provided password matches the user's password
        return passwordEncoder.matches(providedPassword, userDetails.getPassword());
    }
    @PostMapping("/login-error")
    public ResponseEntity<String> onFailedLogin(
            @RequestBody Map<String, String> loginData) {
        // Process the login error and return an appropriate response
        String username = loginData.get(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY);
        // You can return an error message or status as JSON
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"Login failed for user " + username + "\"}");
    }

}
