package com.apponex.eLibaryManagment.business;

import com.apponex.eLibaryManagment.core.entity.Role;
import com.apponex.eLibaryManagment.core.entity.User;
import com.apponex.eLibaryManagment.core.util.EmailSenderUtil;
import com.apponex.eLibaryManagment.dataAccess.auth.ActivationCodeRepository;
import com.apponex.eLibaryManagment.dataAccess.auth.UserRepository;
import com.apponex.eLibaryManagment.dto.user.ChangePasswordRequest;
import com.apponex.eLibaryManagment.dto.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderUtil emailSenderUtil;
    private final ActivationCodeRepository activationCodeRepository;

    public UserResponse getUserInformation(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        return UserResponse.builder()
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
    public void changePassword(ChangePasswordRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalStateException("Current password is wrong!");
        }
        if (!Objects.equals(request.newPassword(),request.confirmationPassword())) {
            throw new IllegalStateException("This passwords are not same!");
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    public void forgetPassword(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        emailSenderUtil.sendValidationEmail(user);
    }

    public void resetPassword(Authentication connectedUser,String myCode, String newPassword,String confirmationPassword) {
        User user = (User) connectedUser.getPrincipal();
        var code = activationCodeRepository.findByCode(myCode)
                .orElseThrow(()->new EntityNotFoundException("Code not found"));
        if (!Objects.equals(code.getUser().getId(),user.getId())) {
            throw new IllegalStateException("Wrong password!");
        }

        if (LocalDateTime.now().isAfter(code.getExpiresAt())) {
            emailSenderUtil.sendValidationEmail(user);
            throw new IllegalStateException("This code has been expired");
        }

        if (!(newPassword.equals(confirmationPassword))) {
            throw new IllegalStateException("This passwords are not same!");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void deactivateAccount(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        user.setEnabled(false);
        user.setAccountLocked(true);
        userRepository.save(user);
    }

    public UserResponse toSellerAccount(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        user.setRole(Role.SELLER);
        userRepository.save(user);
        return UserResponse.builder()
               .firstName(user.getFirstname())
               .lastName(user.getLastname())
               .email(user.getEmail())
               .role(user.getRole().name())
               .build();
    }
}
