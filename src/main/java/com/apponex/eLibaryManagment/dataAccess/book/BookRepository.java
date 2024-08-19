package com.apponex.eLibaryManagment.dataAccess.book;

import com.apponex.eLibaryManagment.core.entity.User;
import com.apponex.eLibaryManagment.entity.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Integer> {
    Optional<Page<Book>> findAllByOwnerFirstname(Pageable pageable, String bookOwnerName);
    Optional<Page<Book>> findAllByBookName(Pageable pageable, String bookName);
    Optional<Page<Book>> findAllByGenre(Pageable pageable, String genre);
    Optional<Page<Book>> findAllByAuthorName(Pageable pageable, String authorName);
    Optional<Page<Book>> findAllByPriceGreaterThanEqualAndPriceLessThanEqual(Pageable pageable, @RequestParam("min-price") double minPrice, @RequestParam("max-price") double maxPrice);

    Optional<Page<Book>> findAllByOwner(Pageable pageable, User user);
}
