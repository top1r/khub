package com.khub.rest.dto;

import java.util.List;

public class ResponseDto {

    public ResponseDto(String title, List<String> author, String type) {
        this.title = title;
        this.author = author;
        this.type = type;
    }

    private String title;
    private List<String> author;
    private String type;

    public String getTitle() {
        return title;
    }

    public List<String> getAuthor() {
        return author;
    }

    public String getType() {
        return type;
    }

}
