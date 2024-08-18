package com.apponex.eLibaryManagment.api.book;

import com.apponex.eLibaryManagment.business.book.BookService;
import com.apponex.eLibaryManagment.core.common.PageResponse;
import com.apponex.eLibaryManagment.dto.book.BookRequest;
import com.apponex.eLibaryManagment.dto.book.BookResponse;
import com.apponex.eLibaryManagment.dto.book.UpdateBookRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/cover/{book-id}",consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverPicture(
            @PathVariable("book-id") Integer bookId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    ) {
        bookService.uploadCarCoverPicture(file,connectedUser,bookId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/find/{book-name}")
    public ResponseEntity<PageResponse<BookResponse>> findBooksByName(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @PathVariable("book-name") String bookName
    ) {
        return ResponseEntity.ok(bookService.findBooksByName(page,size,bookName));
    }

    @GetMapping("/find/{book-author}")
    public ResponseEntity<PageResponse<BookResponse>> findBooksByAuthorName(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @PathVariable("book-author") String bookName
    ) {
        return ResponseEntity.ok(bookService.findBooksByAuthorName(page,size,bookName));
    }

    @GetMapping("/find/{book-genre}")
    public ResponseEntity<PageResponse<BookResponse>> findBooksByGenre(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @PathVariable("book-genre") String bookName
    ) {
        return ResponseEntity.ok(bookService.findBooksByGenre(page,size,bookName));
    }

    @GetMapping("/find/{book-owner}")
    public ResponseEntity<PageResponse<BookResponse>> findBooksByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @PathVariable("book-owner") String bookOwnerName
    ) {
        return ResponseEntity.ok(bookService.findBooksByOwner(page,size,bookOwnerName));
    }

    @GetMapping("/find/price")
    public ResponseEntity<PageResponse<BookResponse>> findBooksByPrice(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam double minPrice,
            @RequestParam double maxPrice
    ) {
        return ResponseEntity.ok(bookService.findBooksByPrice(page,size,minPrice,maxPrice));
    }

    @PatchMapping("/return/{book-id}")
    public ResponseEntity<BookResponse> returnBook(
            Authentication connectedUser,
            @PathVariable("book-id") Integer bookId
    ) {
        return ResponseEntity.ok(bookService.returnBook(connectedUser,bookId));
    }

}
