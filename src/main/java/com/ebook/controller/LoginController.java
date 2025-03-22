package com.ebook.controller;

import com.ebook.domain.User;
import com.ebook.dto.AuthenticationRequest;
import com.ebook.dto.AuthenticationResponse;
import com.ebook.security.JWTService;
import com.ebook.service.UserService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorization")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;
    private final JWTService jwtService;

    @Autowired
    public LoginController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest authenticationRequest){
        logger.info("Inside logincontroller->login");
        User user = userService.findByEmail(authenticationRequest.getEmail());
        if(user==null){
            logger.info("user is not found with email {}",authenticationRequest.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
        if(userService.authenticateUser(authenticationRequest.getEmail(), authenticationRequest.getPassword())){
            String token = jwtService.generateToken(user);
            logger.info("valid user credentials. User authentication is successfull");
            return ResponseEntity.ok(new AuthenticationResponse(user.getId(), token,String.valueOf(user.getRole())));
        }
        logger.info("Invalid credentials. User authentication is failed");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
    }

    @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String validateToken(@RequestHeader("Authorization") String token){
        try{
            if(token.startsWith("Bearer ")){
                token = token.substring(7);
            }

            Claims claims = jwtService.validateToken(token);
            logger.info("User is validated");
            logger.info("claim.subject {}",claims.getSubject());
            logger.info("claims.getRole {}", claims.get("role",String.class));
            return  claims.getSubject();
        }catch (Exception e){
            logger.info("invalid token. Error:"+ e.getMessage());
            return "Invalid Token";
        }
    }
}

