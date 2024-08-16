package com.apponex.eLibaryManagment.dto.auth;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String token
) {
}
