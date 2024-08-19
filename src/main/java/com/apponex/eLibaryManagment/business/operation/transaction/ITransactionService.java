package com.apponex.eLibaryManagment.business.operation.transaction;

import com.apponex.eLibaryManagment.core.entity.User;
import com.apponex.eLibaryManagment.dto.book.WalletOperationResponse;
import com.apponex.eLibaryManagment.entity.book.Book;

public interface ITransactionService {
    WalletOperationResponse takeBook(User user, Book book);
    boolean returnBook(User user, Book book);
    WalletOperationResponse approveReturnedBook(User user, Book book);
}
