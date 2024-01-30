package com.api.boutiquebuzz.auth;

import com.api.boutiquebuzz.domain.dtos.UserInfo;
import com.api.boutiquebuzz.domain.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

public class AuthenticationController {
    @Autowired
    private UserDetailsService userDetailsService;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    JWTTokenHelper jWTTokenHelper;

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            // Handle the case where the user is not authenticated
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        User userObj = (User) userDetailsService.loadUserByUsername(user.getName());

        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName(userObj.getFirstname());
        userInfo.setLastName(userObj.getLastname());
        userInfo.setRoles(userObj.getAuthorities().toArray());


        return ResponseEntity.ok(userInfo);
    }

//    @GetMapping("/userinfo")
//    public ResponseEntity<?> getUserInfo(Principal user) {
//        if (user == null) {
//            // Handle the case where the user is not authenticated
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
//        }
//        User userObj = (User) userDetailsService.loadUserByUsername(user.getName());
//
//        UserInfo userInfo = new UserInfo();
//        userInfo.setFirstName(userObj.getFirstname());
//        userInfo.setLastName(userObj.getLastname());
//        userInfo.setRoles(userObj.getAuthorities().toArray());
//
//
//        return ResponseEntity.ok(userInfo);
//    }

}