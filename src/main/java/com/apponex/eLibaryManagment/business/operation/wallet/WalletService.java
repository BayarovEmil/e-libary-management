package com.apponex.eLibaryManagment.business.operation.wallet;

import com.apponex.eLibaryManagment.business.cargo.CargoService;
import com.apponex.eLibaryManagment.business.cargo.ICargoService;
import com.apponex.eLibaryManagment.core.common.PageResponse;
import com.apponex.eLibaryManagment.core.entity.User;
import com.apponex.eLibaryManagment.core.exception.OperationNotPermittedException;
import com.apponex.eLibaryManagment.dataAccess.book.BookRepository;
import com.apponex.eLibaryManagment.dataAccess.operation.WalletOperationRepository;
import com.apponex.eLibaryManagment.dataAccess.book.WalletRepository;
import com.apponex.eLibaryManagment.dto.book.WalletOperationResponse;
import com.apponex.eLibaryManagment.dto.book.WalletResponse;
import com.apponex.eLibaryManagment.dto.cargo.CargoResponse;
import com.apponex.eLibaryManagment.dto.cargo.TrackingResponse;
import com.apponex.eLibaryManagment.entity.book.Book;
import com.apponex.eLibaryManagment.entity.wallet.Wallet;
import com.apponex.eLibaryManagment.entity.wallet.WalletOperation;
import com.apponex.eLibaryManagment.mapper.user.UserMapper;
import com.apponex.eLibaryManagment.mapper.wallet.WalletMapper;
import com.apponex.eLibaryManagment.mapper.wallet.WalletOperationMapper;
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
public class WalletService implements IWalletService {
    private final WalletRepository walletRepository;
    private final WalletOperationRepository walletOperationRepository;
    private final UserMapper userMapper;
    private final WalletOperationMapper walletOperationMapper;
    private final WalletMapper walletMapper;
    private final BookRepository bookRepository;
    private final ICargoService cargoService;
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
        wallet.setUser(user);
        return walletMapper.walletResponse(walletRepository.save(wallet));
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
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
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

    public PageResponse<WalletOperationResponse> getBookSellingHistoryByBookId(Authentication connectedUser, Integer bookId, int page, int size) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdAt").descending());
        Book book = bookRepository.findById(bookId)
               .orElseThrow(()->new IllegalStateException("Book not found by id"));
//        if (Objects.equals(book.getOwner().getId(),user.getId())) {
//            throw new OperationNotPermittedException("User is not allowed to view book selling history");
//        }
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
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdAt").descending());
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


    @Override
    public WalletOperationResponse buyWithWallet(User user, Book book) {
        Wallet ownWallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(()->new IllegalStateException("Wallet not found"));
        TrackingResponse order = cargoService.takeOrder(user,book);
        if (ownWallet.getBalance() < book.getPrice() + order.shippingCost()) {
            throw new OperationNotPermittedException("Insufficient funds in wallet");
        }
        ownWallet.setBalance(ownWallet.getBalance() - ( book.getPrice() + order.shippingCost() ) );
        walletRepository.save(ownWallet);
        Wallet sellerWallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(()->new IllegalStateException("Wallet not found"));
        sellerWallet.setBalance(sellerWallet.getBalance() + book.getPrice());
        walletRepository.save(sellerWallet);
        var walletOperation = walletOperationMapper.toWalletOperation(book);
        walletOperationRepository.save(walletOperation);
        return walletOperationMapper.walletOperationResponse(walletOperation);
    }

    @Override
    public WalletOperationResponse returnMoney(User user, Book book) {
        Wallet ownWallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(()->new IllegalStateException("Wallet not found"));
        var order = cargoService.takeOrder(user,book);
        ownWallet.setBalance(ownWallet.getBalance() - ( book.getPrice() + order.shippingCost() ) );
        walletRepository.save(ownWallet);
        Wallet sellerWallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(()->new IllegalStateException("Wallet not found"));
        sellerWallet.setBalance(sellerWallet.getBalance() - book.getPrice());
        walletRepository.save(sellerWallet);
        var walletOperation = walletOperationMapper.toWalletOperation(book);
        walletOperationRepository.save(walletOperation);
        return walletOperationMapper.walletOperationResponse(walletOperation);
    }




}
