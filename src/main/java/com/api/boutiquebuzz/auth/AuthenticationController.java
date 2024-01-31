package com.api.boutiquebuzz.auth;

import com.api.boutiquebuzz.domain.dtos.UserInfo;
import com.api.boutiquebuzz.user.User;
import com.api.boutiquebuzz.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private final UserDetailsService userDetailsService;
    private final AuthenticationService service;
    private final UserService userService;

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
    public ResponseEntity<?> getUserInfo(Principal user, HttpServletResponse response) {
        String userR = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("SecConHolder (5): "+ SecurityContextHolder.getContext());
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");

        if (user == null) {
            // Handle the case where the user is not authenticated
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        User userObj = (User) userDetailsService.loadUserByUsername(user.getName());
        User userObj2 = (User) userService.loadUserByUsername(user.getName());

//        UserInfo userInfo = new UserInfo();
//        userInfo.setFirstName(userObj.getFirstname());
//        userInfo.setLastName(userObj.getLastname());
//        userInfo.setRoles(userObj.getAuthorities().toArray());


        return ResponseEntity.ok(user);
//        return ResponseEntity.ok(userObj);
//        return ResponseEntity.ok(userObj2);
    }
}
