package com.acastrillo.supermarketbackend.controllers;

import com.acastrillo.supermarketbackend.model.MarketList;
import com.acastrillo.supermarketbackend.model.User;
import com.acastrillo.supermarketbackend.repositories.MarketListsRepository;
import com.acastrillo.supermarketbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MarketListsController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MarketListsRepository listsRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping(path = "/lists", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MarketList> getLists(Authentication authentication) {
        User owner = userRepository.findByEmail(authentication.getName());
        return listsRepository.findByOwnerId(owner.getId());
    }

    @PostMapping(path = "/lists", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createList(@RequestBody MarketList list, Authentication authentication) {
        MarketList savedList = null;
        ResponseEntity<String> response = null;
        HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            if(list.getListName() == null || list.getDetails() == null) {
                errorStatus = HttpStatus.BAD_REQUEST;
                throw new Exception("Please send all required data");
            }
            savedList = listsRepository.save(list);
            if (savedList.getId() != null) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(list.toString());
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(errorStatus)
                    .body("{\"message\": \"" + ex.getMessage()+"\"}");
        }
        return response;
    }


}
