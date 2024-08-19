package com.apponex.eLibaryManagment.mapper.wallet;

import com.apponex.eLibaryManagment.dto.book.WalletResponse;
import com.apponex.eLibaryManagment.entity.wallet.Wallet;
import com.apponex.eLibaryManagment.mapper.user.UserMapper;
import com.apponex.eLibaryManagment.mapper.book.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletMapper {
    private final UserMapper userMapper;
    private final BookMapper bookMapper;
    public WalletResponse walletResponse(Wallet wallet) {
        return WalletResponse.builder()
                .balance(wallet.getBalance())
                .userResponse(userMapper.toUserResponse(wallet.getUser()))
                .build();
    }

}
