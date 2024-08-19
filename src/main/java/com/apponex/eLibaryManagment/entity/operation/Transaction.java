package com.apponex.eLibaryManagment.entity.operation;

import com.apponex.eLibaryManagment.core.common.BaseEntity;
import com.apponex.eLibaryManagment.core.entity.User;
import com.apponex.eLibaryManagment.entity.book.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Transaction extends BaseEntity {
    private boolean returned;
    private boolean returnApproved;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
