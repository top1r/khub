
package com.khub.rest.spotify.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {

    @JsonProperty("album_type")
    private String mAlbumType;
    @JsonProperty("artists")
    private List<Artist> mArtists;
    @JsonProperty("available_markets")
    private List<String> mAvailableMarkets;
    @JsonProperty("external_urls")
    private ExternalUrls mExternalUrls;
    @JsonProperty("href")
    private String mHref;
    @JsonProperty("id")
    private String mId;
    @JsonProperty("images")
    private List<Image> mImages;
    @JsonProperty("name")
    private String mName;
    @JsonProperty("type")
    private String mType;
    @JsonProperty("uri")
    private String mUri;

    public String getAlbumType() {
        return mAlbumType;
    }

    public void setAlbumType(String albumType) {
        mAlbumType = albumType;
    }

    public List<Artist> getArtists() {
        return mArtists;
    }

    public void setArtists(List<Artist> artists) {
        mArtists = artists;
    }

    public List<String> getAvailableMarkets() {
        return mAvailableMarkets;
    }

    public void setAvailableMarkets(List<String> availableMarkets) {
        mAvailableMarkets = availableMarkets;
    }

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

    public List<Image> getImages() {
        return mImages;
    }

    public void setImages(List<Image> images) {
        mImages = images;
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
