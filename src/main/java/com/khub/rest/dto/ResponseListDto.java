package com.khub.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseListDto {

    @JsonProperty("response")
    private List<ResponseDto> responseDtoList;

    public ResponseListDto(List<ResponseDto> responseDtoList){
        this.responseDtoList = responseDtoList;
    }

    public List<ResponseDto> getResponseDtoList() {
        return responseDtoList;
    }
}
