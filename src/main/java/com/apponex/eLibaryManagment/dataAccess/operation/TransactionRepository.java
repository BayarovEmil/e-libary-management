package com.apponex.eLibaryManagment.dataAccess.operation;

import com.apponex.eLibaryManagment.entity.operation.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

            @Query("""
            select transaction
            from Transaction transaction
            where transaction.user.id=:userId
            and transaction.book.id=:bookId
            and transaction.returned=false
            and transaction.returnApproved=false
                    """)
    Optional<Transaction> isUserBuyThisBook(Integer userId, Integer bookId);

    @Query("""
            select transaction
            from Transaction transaction
            where transaction.book.id=:bookId
            and transaction.returned=true
            and transaction.returnApproved=false
           """)
    Optional<Transaction> isUserReturnThisBook(Integer bookId);
}
