package com.khub.rest.google.books.model;

/**
 * Created by JacksonGenerator on 5/19/17.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class SaleInfo {
    @JsonProperty("country")
    private String country;
    @JsonProperty("isEbook")
    private Boolean isEbook;
    @JsonProperty("saleability")
    private String saleability;
}