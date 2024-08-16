package com.apponex.eLibaryManagment.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AuthenticationRequest(
        @NotEmpty(message = "Email must not be null")
        @NotBlank(message = "Email must not be null")
        @Email(message = "email format is wrong!")
        String email,
        @NotEmpty(message = "Password must not be null")
        @NotBlank(message = "Password must not be null")
        @Size(min = 4,message = "Password must longer than 4 character")
        String password
) {
}
