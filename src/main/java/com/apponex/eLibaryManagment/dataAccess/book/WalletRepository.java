package com.apponex.eLibaryManagment.dataAccess.book;

import com.apponex.eLibaryManagment.entity.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Integer> {
    Optional<Wallet> findByUserId(Integer userId);
    boolean existsByUserId(Integer userId);
}
