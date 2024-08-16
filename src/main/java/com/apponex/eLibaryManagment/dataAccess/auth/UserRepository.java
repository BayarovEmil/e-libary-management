package com.apponex.eLibaryManagment.dataAccess.auth;

import com.apponex.eLibaryManagment.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}
