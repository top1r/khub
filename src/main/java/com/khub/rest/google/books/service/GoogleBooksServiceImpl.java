package com.khub.rest.google.books.service;

import com.khub.rest.AppConstants;
import com.khub.rest.dto.ResponseListDto;
import com.khub.rest.google.books.adapter.BooksResponseDtoAdapter;
import com.khub.rest.google.books.model.Books;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import static com.khub.rest.helpers.LoggingHelper.*;

@Service
public class GoogleBooksServiceImpl implements GoogleBooksService{

    private static final Logger log = LoggerFactory.getLogger(GoogleBooksServiceImpl.class);
    private static final String CLASSNAME = GoogleBooksServiceImpl.class.getName();

    @Value( "${google.books.limit}" ) private String maxResults;
    @Value( "${google.books.baseurl}" ) private String baseURL;

    private final String URL = "volumes?q={query}&maxResults={maxResults}";

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;


    @Qualifier("gaugeService")
    @Autowired
    private GaugeService gaugeService;

    public ListenableFuture<ResponseListDto> search(String query) {
        String methodName = "search";
        entering(log, CLASSNAME, methodName);

        Long startTime = System.currentTimeMillis();

        try {
            ListenableFuture<ResponseEntity<Books>> result = asyncRestTemplate.getForEntity(baseURL + URL, Books.class, query, maxResults);
            logSentStatistic(log, AppConstants.GOOGLEBOOKS_SERVICE_NAME, query, maxResults);
            return new BooksResponseDtoAdapter(result, startTime, gaugeService);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        exiting(log, CLASSNAME, methodName);
        return null;
    }



}

