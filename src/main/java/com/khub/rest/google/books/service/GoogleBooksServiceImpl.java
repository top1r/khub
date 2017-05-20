package com.khub.rest.google.books.service;

import com.google.common.base.Stopwatch;
import com.khub.rest.AppConstants;
import com.khub.rest.google.books.adapter.BooksResponseDtoAdapter;
import com.khub.rest.google.books.model.Books;
import com.khub.rest.ResponseController;
import com.khub.rest.dto.ResponseListDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

@Service
public class GoogleBooksServiceImpl implements GoogleBooksService{

    private static final Logger log = LoggerFactory.getLogger(ResponseController.class);
    private static final String URL = "https://www.googleapis.com/books/v1/volumes?q={query}&maxResults={maxResults}";
    public static final String CLASSNAME = GoogleBooksServiceImpl.class.getName();

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Async
    @Override
    public ListenableFuture<ResponseListDto> search(String query) {
        return search(query, null);
    }

    @Async
    public ListenableFuture<ResponseListDto> search(String query, String maxResults) {
        String methodName = "search";
        log.info("Entering {} {}", new Object[] {CLASSNAME, methodName});
        if (StringUtils.isEmpty(maxResults)) {
            maxResults = String.valueOf(AppConstants.DEFAULT_QUERY_LIMIT);
        }
        ListenableFuture<ResponseEntity<Books>> result = null;
        try {
            Stopwatch stopwatch = Stopwatch.createStarted();
            result = asyncRestTemplate.getForEntity(URL, Books.class, query, maxResults);
            stopwatch.stop();
            log.info("Sent request to Google Apis; query: {}, max results: {}, elapsed: {}", new Object[] {query, maxResults, stopwatch});

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("Exiting {} {}", new Object[] {CLASSNAME, methodName});
        return new BooksResponseDtoAdapter(result);
    }



}

