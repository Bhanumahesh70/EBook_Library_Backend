package com.ebook.service;

import Repository.UserRepository;
import com.ebook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractCRUDService<User,Long>{

    private final UserRepository userRepository;

    public UserService(JpaRepository<User, Long> repository, UserRepository userRepository) {
        super(repository);
        this.userRepository = userRepository;
    }
}
