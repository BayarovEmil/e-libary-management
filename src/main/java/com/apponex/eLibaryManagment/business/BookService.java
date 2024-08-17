package com.apponex.eLibaryManagment.business;

import com.apponex.eLibaryManagment.core.common.PageResponse;
import com.apponex.eLibaryManagment.core.entity.User;
import com.apponex.eLibaryManagment.core.file.FileStorageService;
import com.apponex.eLibaryManagment.dataAccess.book.BookRepository;
import com.apponex.eLibaryManagment.dto.book.BookRequest;
import com.apponex.eLibaryManagment.dto.book.BookResponse;
import com.apponex.eLibaryManagment.dto.book.UpdateBookRequest;
import com.apponex.eLibaryManagment.entity.Book;
import com.apponex.eLibaryManagment.mapper.BookMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final FileStorageService fileStorageService;
    public ResponseEntity<BookResponse> createBook(BookRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        var book = bookMapper.toBook(request);
        book.setOwner(user);
        bookRepository.save(book);
        return ResponseEntity.ok(bookMapper.toBookResponse(book));
    }

    public ResponseEntity<BookResponse> readBook(Integer bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(()->new IllegalStateException("Book not found by id"));
        return ResponseEntity.ok(bookMapper.toBookResponse(book));
    }

    public PageResponse<BookResponse> readOwnBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllByOwner(pageable, user)
                .orElseThrow(()->new IllegalStateException("No books found by this user"));
        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalPages(),
                books.getTotalElements(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BookResponse> readAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(pageable);
        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalPages(),
                books.getTotalElements(),
                books.isFirst(),
                books.isLast()
        );
    }

    public ResponseEntity<BookResponse> updateBook(Authentication connectedUser, UpdateBookRequest request, Integer bookId) {
        User user = (User) connectedUser.getPrincipal();
        var book = bookRepository.findById(bookId)
               .orElseThrow(()->new IllegalStateException("Book not found by id"));
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new IllegalArgumentException("User does not have access to this book");
        }
        var updatedBook = bookMapper.toBook(request);
        updatedBook.setId(bookId);
        bookRepository.save(updatedBook);
        return ResponseEntity.ok(bookMapper.toBookResponse(updatedBook));
    }

    public void uploadCarCoverPicture(MultipartFile file, Authentication connectedUser, Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new EntityNotFoundException("No car found by id"));
        User user = (User) connectedUser.getPrincipal();
        var profilePicture  = fileStorageService.saveFile(file,bookId, user.getId());
        book.setCover(profilePicture);
        bookRepository.save(book);
    }

    public PageResponse<BookResponse> findBooksByName(int page, int size, String bookName) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllByBookName(pageable,bookName)
                .orElseThrow(()->new IllegalStateException("No books found by this name"));
        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalPages(),
                books.getTotalElements(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BookResponse> findBooksByAuthorName(int page, int size, String authorName) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllByAuthorName(pageable, authorName)
                .orElseThrow(()->new IllegalStateException("No books found by this author"));
        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalPages(),
                books.getTotalElements(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BookResponse> findBooksByGenre(int page, int size, String bookName) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllByGenre(pageable, bookName)
                .orElseThrow(()->new IllegalStateException("No books found by this author"));
        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalPages(),
                books.getTotalElements(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BookResponse> findBooksByOwner(int page, int size, String bookOwnerName) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllByOwnerFirstname(pageable, bookOwnerName)
                .orElseThrow(()->new IllegalStateException("No books found by this author"));
        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalPages(),
                books.getTotalElements(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BookResponse> findBooksByPrice(int page, int size, double minPrice, double maxPrice) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllByPriceGreaterThanEqualAndPriceLessThanEqual(pageable, minPrice, maxPrice)
                .orElseThrow(()->new IllegalStateException("No books found by this price range"));
        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalPages(),
                books.getTotalElements(),
                books.isFirst(),
                books.isLast()
        );
    }


}
