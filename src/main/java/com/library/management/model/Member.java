package com.library.management.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "member")
    private List<Borrowing> borrowings;
}
