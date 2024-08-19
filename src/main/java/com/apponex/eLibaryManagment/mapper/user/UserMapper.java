package com.apponex.eLibaryManagment.mapper.user;

import com.apponex.eLibaryManagment.core.entity.User;
import com.apponex.eLibaryManagment.dto.user.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
