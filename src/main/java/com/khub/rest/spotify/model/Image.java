
package com.khub.rest.spotify.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Image {

    @JsonProperty("height")
    private Long mHeight;
    @JsonProperty("url")
    private String mUrl;
    @JsonProperty("width")
    private Long mWidth;

    public Long getHeight() {
        return mHeight;
    }

    public void setHeight(Long height) {
        mHeight = height;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public Long getWidth() {
        return mWidth;
    }

    public void setWidth(Long width) {
        mWidth = width;
    }

}
