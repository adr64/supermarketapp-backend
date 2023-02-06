package com.acastrillo.supermarketbackend.repositories;

import com.acastrillo.supermarketbackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    public User findByEmail(String email);

    public long count();

}
