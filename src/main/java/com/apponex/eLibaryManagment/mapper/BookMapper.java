package com.apponex.eLibaryManagment.mapper;

import com.apponex.eLibaryManagment.dto.book.BookRequest;
import com.apponex.eLibaryManagment.dto.book.BookResponse;
import com.apponex.eLibaryManagment.dto.book.UpdateBookRequest;
import com.apponex.eLibaryManagment.entity.book.Book;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public BookResponse toBookResponse(Book request) {
        return BookResponse.builder()
                .bookName(request.getBookName())
                .authorName(request.getAuthorName())
                .description(request.getDescription())
                .publicationYear(request.getPublicationYear())
                .price(request.getPrice())
                .quantity(request.getAvailableQuantity())
                .genre(request.getGenre())
                .bookType(request.getType())
                .active(true)
                .build();
    }

    public Book toBook(BookRequest request) {
        return Book.builder()
                .bookName(request.bookName())
                .authorName(request.authorName())
                .description(request.description())
                .publicationYear(request.publicationYear())
                .price(request.price())
                .availableQuantity(request.quantity())
                .genre(request.genre())
                .type(request.bookType())
                .active(true)
                .build();
    }

    public Book toBook(UpdateBookRequest request) {
        return Book.builder()
                .bookName(request.bookName())
                .authorName(request.authorName())
                .description(request.description())
                .publicationYear(request.publicationYear())
                .price(request.price())
                .availableQuantity(request.quantity())
                .genre(request.genre())
                .active(true)
                .build();
    }
}
