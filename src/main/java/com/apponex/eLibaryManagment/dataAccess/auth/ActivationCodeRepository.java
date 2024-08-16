package com.apponex.eLibaryManagment.dataAccess.auth;

import com.apponex.eLibaryManagment.core.entity.ActivationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivationCodeRepository extends JpaRepository<ActivationCode,Integer> {
    Optional<ActivationCode> findByCode(String code);
}
