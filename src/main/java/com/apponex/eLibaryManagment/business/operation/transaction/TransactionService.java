package com.apponex.eLibaryManagment.business.operation.transaction;

import com.apponex.eLibaryManagment.business.cargo.ICargoService;
import com.apponex.eLibaryManagment.business.operation.wallet.IWalletService;
import com.apponex.eLibaryManagment.core.entity.User;
import com.apponex.eLibaryManagment.dataAccess.operation.TransactionRepository;
import com.apponex.eLibaryManagment.dto.book.WalletOperationResponse;
import com.apponex.eLibaryManagment.entity.book.Book;
import com.apponex.eLibaryManagment.entity.operation.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {
    private final TransactionRepository transactionRepository;
    private final IWalletService walletService;
    private final ICargoService cargoService;

    @Override
    public WalletOperationResponse takeBook(User user, Book book) {
        WalletOperationResponse walletOperationResponse = walletService.buyWithWallet(user,book);
        Transaction transaction = Transaction.builder()
                .user(user)
                .book(book)
                .returned(false)
                .returnApproved(false)
                .build();
        transactionRepository.save(transaction);
        return walletOperationResponse;
    }

    @Override
    public boolean returnBook(User user, Book book) {
        var transaction = transactionRepository.isUserBuyThisBook(user.getId(),book.getId())
                .orElseThrow(() -> new IllegalStateException("No transaction found for this book"));
        transaction.setReturned(true);
        transactionRepository.save(transaction);
        return true;
    }

    @Override
    public WalletOperationResponse approveReturnedBook(User user, Book book) {
        var transaction = transactionRepository.isUserReturnThisBook(book.getId())
                .orElseThrow(() -> new IllegalStateException("No transaction found for this book::"+book.getId()));
        transaction.setReturnApproved(true);
        return walletService.returnMoney(user, book);
    }
}
