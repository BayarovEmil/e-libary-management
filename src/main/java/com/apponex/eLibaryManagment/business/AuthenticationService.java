package com.apponex.eLibaryManagment.business;

import com.apponex.eLibaryManagment.core.util.EmailSenderUtil;
import com.apponex.eLibaryManagment.dataAccess.auth.ActivationCodeRepository;
import com.apponex.eLibaryManagment.dataAccess.auth.TokenRepository;
import com.apponex.eLibaryManagment.dataAccess.auth.UserRepository;
import com.apponex.eLibaryManagment.core.email.EmailService;
import com.apponex.eLibaryManagment.core.email.EmailTemplateName;
import com.apponex.eLibaryManagment.core.entity.*;
import com.apponex.eLibaryManagment.core.security.JwtService;
import com.apponex.eLibaryManagment.dto.auth.AuthenticationRequest;
import com.apponex.eLibaryManagment.dto.auth.AuthenticationResponse;
import com.apponex.eLibaryManagment.dto.auth.RegistrationRequest;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final ActivationCodeRepository activationCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailSenderUtil emailSenderUtil;


    public void register(RegistrationRequest request) {
        var user  = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .enabled(false)
                .accountLocked(false)
                .build();
        userRepository.save(user);
        emailSenderUtil.sendValidationEmail(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var claims = new HashMap<String,Object>();
        User user = (User) auth.getPrincipal();
        claims.put("fullName",user.getFullName());

        String token = jwtService.generateToken(claims,(User) auth.getPrincipal());
//        tokenRepository.save(Token.builder()
//                        .token(token)
//                        .tokenType(TokenType.BEARER)
//                        .expired(false)
//                        .revoked(false)
//                        .user(user)
//                .build());
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public void activateAccount(String code) throws MessagingException {
        var activationCode = activationCodeRepository.findByCode(code)
                .orElseThrow(()-> new EntityNotFoundException("Code not found!"));
        if (LocalDateTime.now().isAfter(activationCode.getExpiresAt())) {
            emailSenderUtil.sendValidationEmail(activationCode.getUser());
            throw new IllegalStateException("Activation code has been expired.We send new code your same mail.");
        }

        var user = userRepository.findByEmail(activationCode.getUser().getEmail())
                .orElseThrow(()->new EntityNotFoundException("User not found by email"));
        user.setEnabled(true);
        userRepository.save(user);

        activationCode.setValidatedAt(LocalDateTime.now());
        activationCodeRepository.save(activationCode);

    }


}
