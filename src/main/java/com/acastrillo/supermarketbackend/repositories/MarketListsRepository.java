package com.acastrillo.supermarketbackend.repositories;

import com.acastrillo.supermarketbackend.model.MarketList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketListsRepository extends MongoRepository<MarketList, String> {

    public MarketList findByListName(String listName);

    public List<MarketList> findByOwnerId(String ownerId);

    public long count();

}
