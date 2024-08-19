package com.apponex.eLibaryManagment.dto.book;

import com.apponex.eLibaryManagment.dto.user.UserResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record WalletResponse(
        double balance,
        UserResponse userResponse,
        List<WalletOperationResponse> walletOperationResponse
) {
}
