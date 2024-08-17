package com.apponex.eLibaryManagment.core.util;

import com.apponex.eLibaryManagment.core.email.EmailService;
import com.apponex.eLibaryManagment.core.email.EmailTemplateName;
import com.apponex.eLibaryManagment.core.entity.ActivationCode;
import com.apponex.eLibaryManagment.core.entity.User;
import com.apponex.eLibaryManagment.dataAccess.auth.ActivationCodeRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailSenderUtil {
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;
    private final EmailService emailService;
    private final ActivationCodeRepository activationCodeRepository;
    public void sendValidationEmail(User user) {
        String activationCode = generateAndSaveActivationCode(user);
        String url;
        try {
            emailService.sendEmail(
                    user.getEmail(),
                    user.getUsername(),
                    EmailTemplateName.ACTIVATE_ACCOUNT,
                    activationUrl,
                    activationCode,
                    "Account activation"
            );
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateAndSaveActivationCode(User user) {
        String code = generateActivationCode(6);

        ActivationCode activationCode = ActivationCode.builder()
                .user(user)
                .code(code)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();
        activationCodeRepository.save(activationCode);
        return code;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder codeBuilder = new StringBuilder();

        for (int i=0;i<length;i++) {
            int randomIndex = secureRandom.nextInt(length);
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
