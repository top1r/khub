package com.khub.rest;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
import com.khub.rest.dto.ResponseDto;
import com.khub.rest.google.books.service.GoogleBooksService;
import com.khub.rest.spotify.service.SpotifyService;
import com.khub.rest.dto.ResponseListDto;
import net.javacrumbs.futureconverter.springguava.FutureConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
public class ResponseController {

    private static final Logger log = LoggerFactory.getLogger(ResponseController.class);

    private final GoogleBooksService googleBooksService;

    @Autowired
    private SpotifyService spotifyService;

    @Autowired
    public ResponseController(GoogleBooksService googleBooksService) {
        this.googleBooksService = googleBooksService;
    }

    @RequestMapping("/test" )
    ListenableFuture response(@RequestParam(required = true) String query){

        Stopwatch stopwatch = Stopwatch.createStarted();

        ListenableFuture bookFuture = googleBooksService.search(query);
        ListenableFuture albumFeature = spotifyService.search(query);

        com.google.common.util.concurrent.ListenableFuture bookGuavaFuture = FutureConverter.toGuavaListenableFuture(bookFuture);
        com.google.common.util.concurrent.ListenableFuture albumGuavaFuture = FutureConverter.toGuavaListenableFuture(albumFeature);

        List<com.google.common.util.concurrent.ListenableFuture<ResponseListDto>> futures = new ArrayList<com.google.common.util.concurrent.ListenableFuture<ResponseListDto>>();
        futures.add(bookGuavaFuture);
        futures.add(albumGuavaFuture);

        AsyncFunction<List<ResponseListDto>, ResponseListDto> mergeFunction = new AsyncFunction<List<ResponseListDto>, ResponseListDto>() {
            @Override
            public com.google.common.util.concurrent.ListenableFuture<ResponseListDto> apply(List<ResponseListDto> responseListDtos) throws Exception {
                List<ResponseDto> resultResponseList = new ArrayList<>();
                for (ResponseListDto dto: responseListDtos){
                    //TODO better checking for nulls
                    if (dto != null && !dto.getResponseDtoList().isEmpty()){
                        resultResponseList.addAll(dto.getResponseDtoList());
                    }
                }
                SettableFuture<ResponseListDto> future = SettableFuture.create();
                Collections.sort(resultResponseList, Comparator.comparing(ResponseDto::getTitle));

                future.set(new ResponseListDto(resultResponseList));
                return future;
            }
        };
        com.google.common.util.concurrent.ListenableFuture<List<ResponseListDto>> collectedResults = Futures.successfulAsList(futures);
        com.google.common.util.concurrent.ListenableFuture<ResponseListDto> guavaResult = Futures.transformAsync(collectedResults, mergeFunction);

        stopwatch.stop();
        log.info("query: {}, elapsed: {}", new Object[] {query, stopwatch});

        return FutureConverter.toSpringListenableFuture(guavaResult);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity handleException(MissingServletRequestParameterException e) {
        log.error("Failed to fetch result from remote service", e);
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        log.error("Failed to fetch result from remote service", e);
        return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
    }

}
