package com.acastrillo.supermarketbackend.controllers;

import com.acastrillo.supermarketbackend.model.User;
import com.acastrillo.supermarketbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        User savedUser = null;
        ResponseEntity<String> response = null;
        HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            if(user.getEmail() == null || user.getPassword() == null || user.getName() == null) {
                errorStatus = HttpStatus.BAD_REQUEST;
                throw new Exception("Please send all required data");
            }
            if(userRepository.findByEmail(user.getEmail()) != null) {
                errorStatus = HttpStatus.BAD_REQUEST;
                throw new Exception("Email provided is already registered");
            }
            String hashPwd = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPwd);
            savedUser = userRepository.save(user);
            if (savedUser.getId() != null) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(user.toString());
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(errorStatus)
                    .body("{\"message\": \"" + ex.getMessage()+"\"}");
        }
        return response;
    }

    @PostMapping(path = "/users/login", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getInfoAfterLogin(Authentication authentication) {
        ResponseEntity<String> response = null;
        HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            User user = userRepository.findByEmail(authentication.getName());
            response = ResponseEntity.
                    status(HttpStatus.OK).body(user.toString());
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(errorStatus)
                    .body("{\"message\": \"" + ex.getMessage()+"\"}");
        }
        return response;
    }
}
