package com.ebook.domain;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "User")
@NamedQuery(name="User.findAll",query="select u from User u")
public class User {

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;



}
