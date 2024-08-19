package com.apponex.eLibaryManagment.business.auth;

import com.apponex.eLibaryManagment.core.common.PageResponse;
import com.apponex.eLibaryManagment.core.entity.Role;
import com.apponex.eLibaryManagment.core.entity.User;
import com.apponex.eLibaryManagment.dataAccess.auth.UserRepository;
import com.apponex.eLibaryManagment.dto.user.UserResponse;
import com.apponex.eLibaryManagment.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public UserResponse getUserInfo(Integer userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(()-> new UsernameNotFoundException("User not found by id"));
        return userMapper.toUserResponse(user);
    }

    public PageResponse<UserResponse> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<User> users = userRepository.findAll(pageable);
        List<UserResponse> userResponses = users.stream()
                .map(userMapper::toUserResponse)
                .toList();
        return new PageResponse<>(
                userResponses,
                users.getNumber(),
                users.getSize(),
                users.getTotalPages(),
                users.getTotalElements(),
                users.isFirst(),
                users.isLast()
        );
    }

    public UserResponse setAdmin(Integer userId) {
        var user = userRepository.findById(userId)
               .orElseThrow(()-> new UsernameNotFoundException("User not found by id"));
        user.setRole(Role.ADMIN);
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
