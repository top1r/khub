package com.khub.rest.google.books.service;

import com.khub.rest.dto.ResponseListDto;
import org.springframework.util.concurrent.ListenableFuture;

public interface GoogleBooksService {
    ListenableFuture<ResponseListDto> search(String query);
}
