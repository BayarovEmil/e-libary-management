package com.apponex.eLibaryManagment.business.operation.wallet;

import com.apponex.eLibaryManagment.core.entity.User;
import com.apponex.eLibaryManagment.dto.book.WalletOperationResponse;
import com.apponex.eLibaryManagment.entity.book.Book;

public interface IWalletService {
    WalletOperationResponse buyWithWallet(User user, Book book);
    WalletOperationResponse returnMoney(User user, Book book);

}
