package com.apponex.eLibaryManagment.dto.user;

import lombok.Builder;

@Builder
public record UserResponse(
        String firstName,
        String lastName,
        String email,
        String role
) {
}
