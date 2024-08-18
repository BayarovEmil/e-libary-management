package com.apponex.eLibaryManagment.dto.book;

public record CardPaymentRequest(
        String cardNumber,
        String expirationDate,
        String cvv
) {
}
