package com.apponex.eLibaryManagment.api;

import com.apponex.eLibaryManagment.business.AdminService;
import com.apponex.eLibaryManagment.core.common.PageResponse;
import com.apponex.eLibaryManagment.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Controller")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/user/{user-id}")
    public ResponseEntity<UserResponse> getUserInfo(
            @PathVariable("user-id") Integer userId
    ) {
        return ResponseEntity.ok(adminService.getUserInfo(userId));
    }

    @GetMapping("/users")
    public ResponseEntity<PageResponse<UserResponse>> getAllUsers(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "10",required = false) int size
    ) {
        return ResponseEntity.ok(adminService.getAllUsers(page,size));
    }

    @PatchMapping("/user/{user-id}")
    public ResponseEntity<UserResponse> setAdmin(
            @PathVariable("user-id") Integer userId
    ) {
        return ResponseEntity.ok(adminService.setAdmin(userId));
    }
}
