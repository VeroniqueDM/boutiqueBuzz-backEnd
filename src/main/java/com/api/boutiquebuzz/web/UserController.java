package com.api.boutiquebuzz.web;

import com.api.boutiquebuzz.domain.dtos.UserDTO;
import com.api.boutiquebuzz.domain.dtos.UserProfileDto;
import com.api.boutiquebuzz.domain.entities.UserEntity;
import com.api.boutiquebuzz.domain.entities.UserRole;
import com.api.boutiquebuzz.repositories.UserEntityRepository;
import com.api.boutiquebuzz.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
private final ModelMapper modelMapper;
    private final UserService userService;
    private final UserEntityRepository userRepository;
    @GetMapping("/user")
    public UserDTO getUser(Authentication authentication) {
        // Retrieve the user information from the Authentication object
        DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();
        UserEntity owner = userRepository.findByEmail(userDetails.getAttribute("email")).get();

        // Create a UserDTO with the relevant user information
        UserDTO userDTO = modelMapper.map(owner,UserDTO.class);
//        userDTO.setUsername(userDetails.getAttributes().get("login")); // You can add other user details
//        userDTO.setId(userDetails.ge);
        return userDTO;
    }
    @PostMapping("/user/{email}")
    public void changeToAdmin(@PathVariable String email) {
        userService.findByEmail(email).ifPresent(userEntity -> {
            userEntity.setRole(UserRole.ROLE_ADMIN);
            userService.save(userEntity);
        });
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // Clear the authentication and invalidate the session to log out the user
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return ResponseEntity.ok("Logout successful");
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getUserById(@PathVariable Long id) {
        UserProfileDto userDTO = userService.getOneById(id);
        return ResponseEntity.ok(userDTO);
    }
}
