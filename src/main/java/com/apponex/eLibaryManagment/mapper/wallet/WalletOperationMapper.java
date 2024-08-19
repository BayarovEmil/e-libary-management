package com.apponex.eLibaryManagment.mapper.wallet;

import com.apponex.eLibaryManagment.dto.book.WalletOperationResponse;
import com.apponex.eLibaryManagment.entity.book.Book;
import com.apponex.eLibaryManagment.entity.wallet.WalletOperation;
import com.apponex.eLibaryManagment.mapper.book.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WalletOperationMapper {
    private final BookMapper bookMapper;

    public WalletOperationResponse walletOperationResponse(WalletOperation request) {
        return WalletOperationResponse.builder()
                .amount(request.getAmount())
                .sellerName(request.getSellerName())
                .bookResponse(bookMapper.toBookResponse(request.getBook()))
                .operationDate(LocalDateTime.now())
                .build();
    }

    public WalletOperation toWalletOperation(Book book) {
        return WalletOperation.builder()
                .amount(-book.getPrice())
                .book(book)
                .sellerName(book.getOwner().getFirstname())
                .wallet(book.getOwner().getWallet())
                .build();
    }
}
