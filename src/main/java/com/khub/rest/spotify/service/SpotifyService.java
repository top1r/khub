package com.khub.rest.spotify.service;

import com.khub.rest.dto.ResponseListDto;
import org.springframework.util.concurrent.ListenableFuture;

public interface SpotifyService {
    ListenableFuture<ResponseListDto> search(String query);
}
