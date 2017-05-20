package com.khub.rest.google.books.model;

/**
 * Created by JacksonGenerator on 5/19/17.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Books {
    @JsonProperty("totalItems")
    private Integer totalItems;
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("items")
    private List<ItemsItem> items;

    public Integer getTotalItems() {
        return totalItems;
    }

    public String getKind() {
        return kind;
    }

    public List<ItemsItem> getItems() {
        return items;
    }
}