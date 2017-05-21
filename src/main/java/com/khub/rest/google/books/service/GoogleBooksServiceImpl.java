package com.khub.rest.google.books.service;

import com.khub.rest.ResponseController;
import com.khub.rest.dto.ResponseListDto;
import com.khub.rest.google.books.adapter.BooksResponseDtoAdapter;
import com.khub.rest.google.books.model.Books;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

@Service
public class GoogleBooksServiceImpl implements GoogleBooksService{

    private static final Logger log = LoggerFactory.getLogger(ResponseController.class);
    private static final String CLASSNAME = GoogleBooksServiceImpl.class.getName();

    @Value( "${google.books.limit}" ) private String maxResults;
    @Value( "${google.books.baseurl}" ) private String baseURL;

    private final String URL = "volumes?q={query}&maxResults={maxResults}";

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    public ListenableFuture<ResponseListDto> search(String query) {
        String methodName = "search";
        log.debug("Entering {} {}", new Object[] {CLASSNAME, methodName});
        ListenableFuture<ResponseEntity<Books>> result = null;
        Long startTime = System.currentTimeMillis();
        try {
            result = asyncRestTemplate.getForEntity(baseURL + URL, Books.class, query, maxResults);
            log.info("Sent request to Google Apis; query: {}, max results: {}", new Object[] {query, maxResults});

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("Exiting {} {}", new Object[] {CLASSNAME, methodName});
        return new BooksResponseDtoAdapter(result, startTime);
    }



}

