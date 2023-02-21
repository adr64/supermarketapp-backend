package com.acastrillo.supermarketbackend.controllers;

import com.acastrillo.supermarketbackend.model.BadRequestException;
import com.acastrillo.supermarketbackend.model.User;
import com.acastrillo.supermarketbackend.model.dto.UserDto;
import com.acastrillo.supermarketbackend.model.dto.UserResponseDto;
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
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserDto userDto) {
        UserResponseDto responseDto;
        try {
            if(userDto.getEmail() == null || userDto.getPassword() == null || userDto.getName() == null) {
                throw new BadRequestException("Please send all required data");
            }
            if(userRepository.findByEmail(userDto.getEmail()) != null) {
                throw new BadRequestException("Email provided is already registered");
            }
            String hashPwd = passwordEncoder.encode(userDto.getPassword());
            User user = new User(userDto);
            User savedUser = userRepository.save(user);
            responseDto = new UserResponseDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), "Success");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (BadRequestException ex) {
            responseDto = new UserResponseDto(null, null, null, ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            responseDto = new UserResponseDto(null, null, null, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @PostMapping(path = "/users/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> getInfoAfterLogin(Authentication authentication) {
        UserResponseDto responseDto;
        try {
            User user = userRepository.findByEmail(authentication.getName());
            responseDto = new UserResponseDto(user);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } catch (Exception ex) {
            responseDto = new UserResponseDto(null, null, null, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

}
