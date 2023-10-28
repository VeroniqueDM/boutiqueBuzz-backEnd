package com.api.boutiquebuzz.domain.dtos.user;

import com.api.boutiquebuzz.validations.PasswordMatcher;
import com.api.boutiquebuzz.validations.UniqueEmail;
import com.api.boutiquebuzz.validations.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@PasswordMatcher(password = "password", confirmPassword = "confirmPassword")
public class UserRegisterFormDto {
@UniqueUsername
private String username;

    @NotBlank(message = "{userregisterformdto.email.invalid}")
    @Email(message = "{userregisterformdto.email.invalid}")
//    @NotBlank(message = InvalidMessages.EMPTY_EMAIL)
//    @Email(message = InvalidMessages.INVALID_EMAIL)
    @UniqueEmail
//    @UniqueElements
    private String email;

    @NotNull
    @Size(min = 5, max = 20, message = "{userregisterformdto.password.size}")
    private String password;

    @NotNull(message = "{userregisterformdto.confirmPassword.notnull}")
    @Size(min = 5, max = 20, message = "{userregisterformdto.confirmPassword.size}")
    private String confirmPassword;

//    @NotNull
//    @Size(min = 5, max = 20)
    private String firstName;

//    @NotNull
//    @Size(min = 5, max = 20)
    private String lastName;

    public String getEmail() {
        return email;
    }

    public UserRegisterFormDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterFormDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterFormDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterFormDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterFormDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
