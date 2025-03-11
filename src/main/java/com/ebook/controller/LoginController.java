package com.ebook.controller;

import com.ebook.domain.User;
import com.ebook.dto.AuthenticationRequest;
import com.ebook.dto.AuthenticationResponse;
import com.ebook.security.JWTService;
import com.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserService userService;
    private final JWTService jwtService;

    @Autowired
    public LoginController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public ResponseEntity<?> login(AuthenticationRequest authenticationRequest){

        User user = userService.findByEmail(authenticationRequest.getEmail());
        if(userService.authenticateUser(authenticationRequest.getEmail(), authenticationRequest.getPassword())){
            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(new AuthenticationResponse(user.getId(), token,String.valueOf(user.getRole())));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
    }
}
