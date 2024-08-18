package com.apponex.eLibaryManagment.business.book;

import com.apponex.eLibaryManagment.core.common.PageResponse;
import com.apponex.eLibaryManagment.core.entity.User;
import com.apponex.eLibaryManagment.core.exception.OperationNotPermittedException;
import com.apponex.eLibaryManagment.dataAccess.book.BookRepository;
import com.apponex.eLibaryManagment.dataAccess.book.WalletOperationRepository;
import com.apponex.eLibaryManagment.dataAccess.book.WalletRepository;
import com.apponex.eLibaryManagment.dto.book.WalletOperationResponse;
import com.apponex.eLibaryManagment.dto.book.WalletResponse;
import com.apponex.eLibaryManagment.entity.Book;
import com.apponex.eLibaryManagment.entity.Wallet;
import com.apponex.eLibaryManagment.entity.WalletOperation;
import com.apponex.eLibaryManagment.mapper.UserMapper;
import com.apponex.eLibaryManagment.mapper.WalletMapper;
import com.apponex.eLibaryManagment.mapper.WalletOperationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final WalletOperationRepository walletOperationRepository;
    private final UserMapper userMapper;
    private final WalletOperationMapper walletOperationMapper;
    private final WalletMapper walletMapper;
    private final BookRepository bookRepository;

    public WalletResponse getWalletInformation(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        var wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(()->new IllegalStateException("Could not find wallet"));
        return WalletResponse.builder()
//                .walletOperationResponse(
//                        WalletOperationResponse.builder()
////                                .amount()
//                                .build()
//                )
                .balance(wallet.getBalance())
                .userResponse(userMapper.toUserResponse(user))
                .build();
    }

    public WalletResponse createWallet(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        if (walletRepository.existsByUserId(user.getId())) {
            throw new IllegalStateException("User already has a wallet");
        }
        Wallet wallet = Wallet.builder()
               .balance(25.0)
               .build();
        return walletMapper.walletResponse(wallet);
    }

    public WalletResponse increaseWalletBalance(Authentication connectedUser, double amount) {
        User user = (User) connectedUser.getPrincipal();
        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(()->new IllegalStateException("Wallet not found"));
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);
        return walletMapper.walletResponse(wallet);
    }

    public PageResponse<WalletOperationResponse> getWalletOperations(Authentication connectedUser, int page, int size) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<WalletOperation> walletOperations = walletOperationRepository.findAllByWalletUserId(pageable,user.getId())
                .orElseThrow(()->new IllegalStateException("Could not find"));
        List<WalletOperationResponse> walletOperationResponses = walletOperations.stream()
                .map(walletOperationMapper::walletOperationResponse)
                .toList();
        return new PageResponse<>(
                walletOperationResponses,
                walletOperations.getNumber(),
                walletOperations.getSize(),
                walletOperations.getTotalPages(),
                walletOperations.getTotalElements(),
                walletOperations.isFirst(),
                walletOperations.isLast()
        );
    }

    public WalletOperationResponse buyWithWallet(Authentication connectedUser, Integer bookId) {
        User user = (User) connectedUser.getPrincipal();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new IllegalStateException("Book not found by id"));
        // Check if user has sufficient balance
        Wallet wallet = walletRepository.findByUserId(user.getId())
               .orElseThrow(()->new IllegalStateException("Wallet not found"));
        // Check if user has sufficient balance
        if (wallet.getBalance() < book.getPrice()) {
            throw new OperationNotPermittedException("Insufficient funds in wallet");
        }
        // Decrease user's wallet balance
        wallet.setBalance(wallet.getBalance() - book.getPrice());
        walletRepository.save(wallet);
        walletRepository.save(book.getOwner().getWallet()).setBalance(wallet.getBalance() + book.getPrice());

        // decrease book quantities
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);
        // Create wallet operation for purchase
        WalletOperation walletOperation = WalletOperation.builder()
               .amount(-book.getPrice())
                .book(book)
                .sellerName(book.getOwner().getFirstname())
               .wallet(wallet)
               .build();
        walletOperationRepository.save(walletOperation);
        // Return wallet operation response
        return walletOperationMapper.walletOperationResponse(walletOperation);
    }


    public PageResponse<WalletOperationResponse> getBookSellingHistoryByBookId(Authentication connectedUser, Integer bookId, int page, int size) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdDate").descending());
        Book book = bookRepository.findById(bookId)
               .orElseThrow(()->new IllegalStateException("Book not found by id"));
        if (Objects.equals(book.getOwner().getId(),user.getId())) {
            throw new OperationNotPermittedException("User is not allowed to view book selling history");
        }
        Page<WalletOperation> walletOperations = walletOperationRepository.findAllByBookId(pageable,book.getId())
               .orElseThrow(()->new IllegalStateException("Could not find operation by id"));
        List<WalletOperationResponse> walletOperationResponses = walletOperations.stream()
                .map(walletOperationMapper::walletOperationResponse)
                .toList();
        return new PageResponse<>(
                walletOperationResponses,
                walletOperations.getNumber(),
                walletOperations.getSize(),
                walletOperations.getTotalPages(),
                walletOperations.getTotalElements(),
                walletOperations.isFirst(),
                walletOperations.isLast()
        );
    }

    public PageResponse<WalletOperationResponse> getBookSellingAllHistory(Authentication connectedUser, int page, int size) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdDate").descending());
        Page<WalletOperation> walletOperations = walletOperationRepository.findAllByWalletUserId(pageable, user.getId())
                .orElseThrow(()->new IllegalStateException("Could not find operation by id"));
        List<WalletOperationResponse> walletOperationResponses = walletOperations.stream()
                .map(walletOperationMapper::walletOperationResponse)
                .toList();
        return new PageResponse<>(
                walletOperationResponses,
                walletOperations.getNumber(),
                walletOperations.getSize(),
                walletOperations.getTotalPages(),
                walletOperations.getTotalElements(),
                walletOperations.isFirst(),
                walletOperations.isLast()
        );
    }

    public WalletOperationResponse reverseMoneyWithWallet(Authentication connectedUser, Integer bookId) {
        User user = (User) connectedUser.getPrincipal();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new IllegalStateException("Book not found by id"));
        // Check if user has sufficient balance
        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(()->new IllegalStateException("Wallet not found"));
        // Check if user has sufficient balance
        if (wallet.getBalance() < book.getPrice()) {
            throw new OperationNotPermittedException("Insufficient funds in wallet");
        }
        // Decrease user's wallet balance
        wallet.setBalance(wallet.getBalance() + book.getPrice());
        walletRepository.save(wallet);
        walletRepository.save(book.getOwner().getWallet()).setBalance(wallet.getBalance() - book.getPrice());

        // decrease book quantities
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);
        // Create wallet operation for purchase
        WalletOperation walletOperation = WalletOperation.builder()
                .amount(-book.getPrice())
                .book(book)
                .sellerName(book.getOwner().getFirstname())
                .wallet(wallet)
                .build();
        walletOperationRepository.save(walletOperation);
        // Return wallet operation response
        return walletOperationMapper.walletOperationResponse(walletOperation);
    }
}
