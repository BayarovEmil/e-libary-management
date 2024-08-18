package com.apponex.eLibaryManagment.dto.book;

import com.apponex.eLibaryManagment.dto.user.UserResponse;
import com.apponex.eLibaryManagment.entity.WalletOperation;
import lombok.Builder;

import java.util.List;

@Builder
public record WalletResponse(
        double balance,
        UserResponse userResponse,
        List<WalletOperationResponse> walletOperationResponse
) {
}
