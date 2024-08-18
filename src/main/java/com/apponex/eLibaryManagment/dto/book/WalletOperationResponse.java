package com.apponex.eLibaryManagment.dto.book;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record WalletOperationResponse(
        double amount,
        String sellerName,
        BookResponse bookResponse,
        LocalDateTime operationDate
) {
}
