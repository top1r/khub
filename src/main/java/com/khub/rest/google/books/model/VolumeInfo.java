package com.khub.rest.google.books.model;

/**
 * Created by JacksonGenerator on 5/19/17.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class VolumeInfo {
    @JsonProperty("industryIdentifiers")
    private List<IndustryIdentifiersItem> industryIdentifiers;
    @JsonProperty("pageCount")
    private Integer pageCount;
    @JsonProperty("printType")
    private String printType;
    @JsonProperty("readingModes")
    private ReadingModes readingModes;
    @JsonProperty("previewLink")
    private String previewLink;
    @JsonProperty("canonicalVolumeLink")
    private String canonicalVolumeLink;
    @JsonProperty("description")
    private String description;
    @JsonProperty("language")
    private String language;
    @JsonProperty("title")
    private String title;
    @JsonProperty("imageLinks")
    private ImageLinks imageLinks;
    @JsonProperty("averageRating")
    private Double averageRating;
    @JsonProperty("publisher")
    private String publisher;
    @JsonProperty("ratingsCount")
    private Integer ratingsCount;
    @JsonProperty("publishedDate")
    private String publishedDate;
    @JsonProperty("categories")
    private List<String> categories;
    @JsonProperty("maturityRating")
    private String maturityRating;
    @JsonProperty("allowAnonLogging")
    private Boolean allowAnonLogging;
    @JsonProperty("contentVersion")
    private String contentVersion;
    @JsonProperty("authors")
    private List<String> authors;
    @JsonProperty("infoLink")
    private String infoLink;

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }
}