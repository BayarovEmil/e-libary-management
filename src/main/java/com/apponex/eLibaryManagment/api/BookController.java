package com.apponex.eLibaryManagment.api;

import com.apponex.eLibaryManagment.business.BookService;
import com.apponex.eLibaryManagment.core.common.PageResponse;
import com.apponex.eLibaryManagment.dto.book.BookRequest;
import com.apponex.eLibaryManagment.dto.book.BookResponse;
import com.apponex.eLibaryManagment.dto.book.UpdateBookRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@Tag(name = "Book Controller")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<BookResponse> createNewBook(
            @RequestBody BookRequest request,
            Authentication connectedUser
    ) {
        return bookService.createBook(request, connectedUser);
    }

    @GetMapping("/read/{book-id}")
    public ResponseEntity<BookResponse> readBook(
            @PathVariable("book-id") Integer bookId
    ) {
        return bookService.readBook(bookId);
    }

    @GetMapping("/readOwnBooks")
    public ResponseEntity<PageResponse<BookResponse>> readOwnBooks(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.readOwnBooks(page,size,connectedUser));
    }

    @GetMapping("/readAllBooks")
    public ResponseEntity<PageResponse<BookResponse>> readAllBooks(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        return ResponseEntity.ok(bookService.readAllBooks(page,size));
    }

    @PutMapping("/update/{book-id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser,
            @RequestBody UpdateBookRequest request
    ) {
        return bookService.updateBook(connectedUser,request, bookId);
    }
}
