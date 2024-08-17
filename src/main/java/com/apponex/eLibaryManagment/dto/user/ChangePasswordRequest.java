package com.apponex.eLibaryManagment.dto.user;

public record ChangePasswordRequest(
        String currentPassword,
        String newPassword,
        String confirmationPassword
) {
}
