
package com.khub.rest.spotify.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Artist {

    @JsonProperty("external_urls")
    private ExternalUrls mExternalUrls;
    @JsonProperty("href")
    private String mHref;
    @JsonProperty("id")
    private String mId;
    @JsonProperty("name")
    private String mName;
    @JsonProperty("type")
    private String mType;
    @JsonProperty("uri")
    private String mUri;

    public ExternalUrls getExternalUrls() {
        return mExternalUrls;
    }

    public void setExternalUrls(ExternalUrls externalUrls) {
        mExternalUrls = externalUrls;
    }

    public String getHref() {
        return mHref;
    }

    public void setHref(String href) {
        mHref = href;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

}
