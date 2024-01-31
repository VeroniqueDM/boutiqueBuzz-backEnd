package com.api.boutiquebuzz.user;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserDetailsService userDetailsService;
    private final UserService service;

    @PatchMapping
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(Principal user, HttpServletResponse response) {
        System.out.println("SecConHolder (3): "+ SecurityContextHolder.getContext());
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");

        if (user == null) {
            // Handle the case where the user is not authenticated
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        User userObj = (User) userDetailsService.loadUserByUsername(user.getName());
        User userObj2 = (User) service.loadUserByUsername(user.getName());
        System.out.println("Success!");
//        UserInfo userInfo = new UserInfo();
//        userInfo.setFirstName(userObj.getFirstname());
//        userInfo.setLastName(userObj.getLastname());
//        userInfo.setRoles(userObj.getAuthorities().toArray());


//        return ResponseEntity.ok(user);
        ResponseEntity<?> res = ResponseEntity.ok(userObj);
        System.out.println("Response: " + res);
        return ResponseEntity.ok(userObj);
//        return ResponseEntity.ok(userObj2);
    }
}
