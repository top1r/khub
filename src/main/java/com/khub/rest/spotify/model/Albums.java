package com.khub.rest.spotify.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Albums {

    @JsonProperty("albums")
    private Albums mAlbums;
    @JsonProperty("href")
    private String mHref;
    @JsonProperty("items")
    private List<Item> mItems;
    @JsonProperty("limit")
    private Long mLimit;
    @JsonProperty("next")
    private String mNext;
    @JsonProperty("offset")
    private Long mOffset;
    @JsonProperty("previous")
    private Object mPrevious;
    @JsonProperty("total")
    private Long mTotal;

    public Albums getAlbums() {
        return mAlbums;
    }

    public void setAlbums(Albums albums) {
        mAlbums = albums;
    }

    public String getHref() {
        return mHref;
    }

    public void setHref(String href) {
        mHref = href;
    }

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }

    public Long getLimit() {
        return mLimit;
    }

    public void setLimit(Long limit) {
        mLimit = limit;
    }

    public String getNext() {
        return mNext;
    }

    public void setNext(String next) {
        mNext = next;
    }

    public Long getOffset() {
        return mOffset;
    }

    public void setOffset(Long offset) {
        mOffset = offset;
    }

    public Object getPrevious() {
        return mPrevious;
    }

    public void setPrevious(Object previous) {
        mPrevious = previous;
    }

    public Long getTotal() {
        return mTotal;
    }

    public void setTotal(Long total) {
        mTotal = total;
    }

}
