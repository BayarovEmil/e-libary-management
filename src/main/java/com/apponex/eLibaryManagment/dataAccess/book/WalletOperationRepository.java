package com.apponex.eLibaryManagment.dataAccess.book;

import com.apponex.eLibaryManagment.entity.WalletOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletOperationRepository extends JpaRepository<WalletOperation,Integer> {

    Optional<Page<WalletOperation>> findAllByWalletUserId(Pageable pageable,Integer userId);
    Optional<Page<WalletOperation>> findAllByBookId(Pageable pageable, Integer bookId);
    Optional<WalletOperation> findByBookId(Integer bookId);
}
