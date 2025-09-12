package com.library.management.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String title;
    private String isbn;
    private String author;
    private String publisher;
    private Integer publicationYear;
    private Integer quantity;
    private Integer availableQuantity;


}
