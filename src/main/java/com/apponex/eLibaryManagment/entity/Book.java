package com.apponex.eLibaryManagment.entity;

import com.apponex.eLibaryManagment.core.common.BaseEntity;
import com.apponex.eLibaryManagment.core.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Book extends BaseEntity {
    private String bookName;
    private String authorName;
    private LocalDate publicationYear;
    private String description;
    private int availableQuantity;
    private double price;
    private String genre;

    private BookType type;
    private boolean active;
    private String cover;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
