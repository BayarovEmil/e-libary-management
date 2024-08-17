package com.apponex.eLibaryManagment.api;

import com.apponex.eLibaryManagment.business.UserService;
import com.apponex.eLibaryManagment.dto.user.ChangePasswordRequest;
import com.apponex.eLibaryManagment.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/userInfo")
    public ResponseEntity<UserResponse> getUserInformation(
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(userService.getUserInformation(connectedUser));
    }

    @PatchMapping("/password/changePassword")
    public void changePassword(
            @RequestBody ChangePasswordRequest request,
            Authentication connectedUser
    ) {
        userService.changePassword(request,connectedUser);
    }

    @PatchMapping("/password/forgetPassword")
    public void forgetPassword(
            Authentication connectedUser
    ) {
        userService.forgetPassword(connectedUser);
    }

    @GetMapping("/password/resetPassword")
    public void resetPassword(
            Authentication connectedUser,
            @RequestParam String code,
            @RequestParam String newPassword,
            @RequestParam String confirmationPassword
    ) {
        userService.resetPassword(connectedUser,code,newPassword,confirmationPassword);
    }

    @PatchMapping("/deactivatedAccount")
    public void deactivateAccount(
            Authentication connectedUser
    ) {
        userService.deactivateAccount(connectedUser);
    }

    @PatchMapping("/toSellerAccount")
    public ResponseEntity<UserResponse> toSellerAccount(
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(userService.toSellerAccount(connectedUser));
    }
}
