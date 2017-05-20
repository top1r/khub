
package com.khub.rest.spotify.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExternalUrls {

    @JsonProperty("spotify")
    private String mSpotify;

    public String getSpotify() {
        return mSpotify;
    }

    public void setSpotify(String spotify) {
        mSpotify = spotify;
    }

}
