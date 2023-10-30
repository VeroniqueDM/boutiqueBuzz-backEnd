//package com.api.boutiquebuzz.domain.entities;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import com.api.boutiquebuzz.domain.entities.UserEntity;
//
//import java.util.Collection;
//import java.util.Collections;
//
//public class CustomUserDetails implements UserDetails {
//    private UserEntity userEntity;
//
//    public CustomUserDetails(UserEntity userEntity) {
//        this.userEntity = userEntity;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // You can define roles/authorities based on your UserEntity here.
//        return Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole().name()));
//    }
//
//    @Override
//    public String getPassword() {
//        // You may return a password if applicable, or null.
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return userEntity.getUsername();
//    }
//
//    public String getEmail() {
//        return userEntity.getEmail();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        // Implement account expiration logic if needed.
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        // Implement account locking logic if needed.
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        // Implement credentials expiration logic if needed.
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        // Implement logic for account activation or deactivation.
//        return true;
//    }
//}
