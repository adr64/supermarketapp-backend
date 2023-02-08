package com.acastrillo.supermarketbackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("lists")
public class MarketList {

    @Id
    String id;
    String listName;
    String details;

    String ownerId;

    public MarketList(String id, String listName, String ownerId) {
        this.id = id;
        this.listName = listName;
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\": \"" + id + "\"" +
                ", \"listName\": \"" + listName + "\"" +
                ", \"details\": \"" + details + "\"" +
                "}";
    }
}
