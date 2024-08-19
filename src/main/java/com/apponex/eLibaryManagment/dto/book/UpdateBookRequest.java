package com.apponex.eLibaryManagment.dto.book;

import java.time.LocalDate;

public record UpdateBookRequest(
        String bookName,
        String authorName,
        LocalDate publicationYear,
        String description,
        Double price,
        int quantity,
        String genre
) {
}
