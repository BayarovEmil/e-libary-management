package com.apponex.eLibaryManagment.api.book;

import com.apponex.eLibaryManagment.business.operation.wallet.WalletService;
import com.apponex.eLibaryManagment.core.common.PageResponse;
import com.apponex.eLibaryManagment.dto.book.WalletOperationResponse;
import com.apponex.eLibaryManagment.dto.book.WalletResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@Tag(name = "Wallet Controller", description = "Operations related to wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping("/getWallet")
    public ResponseEntity<WalletResponse> getWalletInformation(
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(walletService.getWalletInformation(connectedUser));
    }

    @PostMapping("/create")
    public ResponseEntity<WalletResponse> createWallet(
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(walletService.createWallet(connectedUser));
    }

    @PutMapping("/increase")
    public ResponseEntity<WalletResponse> increaseWalletBalance(
            Authentication connectedUser,
            @RequestParam(name = "amount", defaultValue = "0.0", required = false) double amount
    ) {
        return ResponseEntity.ok(walletService.increaseWalletBalance(connectedUser, amount));
    }

    @GetMapping("/getWalletOperations")
    public ResponseEntity<PageResponse<WalletOperationResponse>> getWalletOperations(
            Authentication connectedUser,
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "10",required = false) int size
    ) {
        return ResponseEntity.ok(walletService.getWalletOperations(connectedUser,page,size));
    }

    @GetMapping("/getBookSellingHistory/{book-id}")
    public ResponseEntity<PageResponse<WalletOperationResponse>> getBookSellingHistory(
            Authentication connectedUser,
            @PathVariable("book-id") Integer bookId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        return ResponseEntity.ok(walletService.getBookSellingHistoryByBookId(connectedUser,bookId,page,size));
    }

    @GetMapping("/getBookAllSellingHistory")
    public ResponseEntity<PageResponse<WalletOperationResponse>> getBookAllSellingHistory(
            Authentication connectedUser,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        return ResponseEntity.ok(walletService.getBookSellingAllHistory(connectedUser,page,size));
    }

}
