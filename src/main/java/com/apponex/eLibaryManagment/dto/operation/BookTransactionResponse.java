package com.apponex.eLibaryManagment.dto.operation;

import com.apponex.eLibaryManagment.dto.book.BookResponse;
import com.apponex.eLibaryManagment.dto.book.WalletOperationResponse;
import lombok.Builder;

@Builder
public record BookTransactionResponse(
        WalletOperationResponse walletOperationResponse
) {
}
