package com.apponex.eLibaryManagment.dto.book;

import com.apponex.eLibaryManagment.entity.BookType;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BookResponse(
        String bookName,
        String authorName,
        LocalDate publicationYear,
        String description,
        Double price,
        int quantity,
        String genre,
        BookType bookType,
        boolean active
) {
}
