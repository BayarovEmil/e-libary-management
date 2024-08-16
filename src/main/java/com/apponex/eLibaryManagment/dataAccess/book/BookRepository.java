package com.apponex.eLibaryManagment.dataAccess.book;

import com.apponex.eLibaryManagment.core.entity.User;
import com.apponex.eLibaryManagment.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Integer> {
    Optional<Page<Book>> findAllByOwner(Pageable pageable, User owner);
}
