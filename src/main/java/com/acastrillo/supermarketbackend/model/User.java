package com.acastrillo.supermarketbackend.model;

import com.acastrillo.supermarketbackend.model.dto.UserDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public class User {
    @Id
    String id;
    public String name;
    String email;
    String password;

    public User() {}

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(UserDto userDto) {
        this.name = userDto.getName();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\": \"" + id + "\"" +
                ", \"name\": \"" + name + "\"" +
                ", \"email\": \"" + email + "\"" +
                "}";
    }
}
