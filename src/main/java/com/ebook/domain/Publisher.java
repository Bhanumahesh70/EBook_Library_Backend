package com.ebook.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;

public class Publisher {

    private  String name;
    private String address;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "publisher",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;
}
