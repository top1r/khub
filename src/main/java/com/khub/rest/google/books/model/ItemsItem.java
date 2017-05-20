package com.khub.rest.google.books.model;

/**
 * Created by JacksonGenerator on 5/19/17.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class ItemsItem {
    @JsonProperty("saleInfo")
    private SaleInfo saleInfo;
    @JsonProperty("searchInfo")
    private SearchInfo searchInfo;
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("volumeInfo")
    private VolumeInfo volumeInfo;
    @JsonProperty("etag")
    private String etag;
    @JsonProperty("id")
    private String id;
    @JsonProperty("accessInfo")
    private AccessInfo accessInfo;
    @JsonProperty("selfLink")
    private String selfLink;

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }
}