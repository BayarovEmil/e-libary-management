package com.apponex.eLibaryManagment.core.config;

import com.apponex.eLibaryManagment.core.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null
        || !authentication.isAuthenticated()
        || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        var user = (User) authentication.getPrincipal();
        return Optional.ofNullable(user.getId());
    }
}
