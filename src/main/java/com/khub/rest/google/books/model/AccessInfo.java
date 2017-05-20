package com.khub.rest.google.books.model;

/**
 * Created by JacksonGenerator on 5/19/17.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class AccessInfo {
    @JsonProperty("accessViewStatus")
    private String accessViewStatus;
    @JsonProperty("country")
    private String country;
    @JsonProperty("viewability")
    private String viewability;
    @JsonProperty("pdf")
    private Pdf pdf;
    @JsonProperty("webReaderLink")
    private String webReaderLink;
    @JsonProperty("epub")
    private Epub epub;
    @JsonProperty("publicDomain")
    private Boolean publicDomain;
    @JsonProperty("quoteSharingAllowed")
    private Boolean quoteSharingAllowed;
    @JsonProperty("embeddable")
    private Boolean embeddable;
    @JsonProperty("textToSpeechPermission")
    private String textToSpeechPermission;
}