package com.ebook.controller;

import com.ebook.domain.User;
import com.ebook.dto.UserDTO;
import com.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ebook/users")
public class UserController extends AbstractController<User, UserDTO,Long> {

    @Autowired
    public UserController(UserService userService) {
        super(userService, User.class);
    }
}
