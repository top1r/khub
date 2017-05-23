package com.khub.rest;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
import com.khub.rest.dto.ResponseDto;
import com.khub.rest.dto.ResponseListDto;
import com.khub.rest.google.books.service.GoogleBooksService;
import com.khub.rest.spotify.service.SpotifyService;
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

    @RequestMapping("/service" )
    ListenableFuture response(@RequestParam String q){
        ListenableFuture bookFuture = googleBooksService.search(q);
        ListenableFuture albumFuture = spotifyService.search(q);
        try {
            com.google.common.util.concurrent.ListenableFuture bookGuavaFuture;
            com.google.common.util.concurrent.ListenableFuture albumGuavaFuture;
            List<com.google.common.util.concurrent.ListenableFuture<ResponseListDto>> futures = new ArrayList<com.google.common.util.concurrent.ListenableFuture<ResponseListDto>>();

            if (null != bookFuture) {
                bookGuavaFuture = FutureConverter.toGuavaListenableFuture(bookFuture);
                if (null != bookGuavaFuture) {
                    futures.add(bookGuavaFuture);
                }

            }
            if (null != albumFuture) {
                albumGuavaFuture = FutureConverter.toGuavaListenableFuture(albumFuture);
                if (null != albumGuavaFuture) {
                    futures.add(albumGuavaFuture);
                }
            }

            AsyncFunction<List<ResponseListDto>, ResponseListDto> mergeFunction = responseListDtos -> {
                List<ResponseDto> resultResponseList = new ArrayList<>();
                if (responseListDtos != null) {
                    for (ResponseListDto dto: responseListDtos){
                        if (null != dto && null != dto.getResponseDtoList() && !dto.getResponseDtoList().isEmpty()){
                            resultResponseList.addAll(dto.getResponseDtoList());
                        }
                    }
                }
                SettableFuture<ResponseListDto> future = SettableFuture.create();
                Collections.sort(resultResponseList, Comparator.comparing(ResponseDto::getTitle));

                future.set(new ResponseListDto(resultResponseList));
                return future;
            };
            com.google.common.util.concurrent.ListenableFuture<List<ResponseListDto>> collectedResults = Futures.successfulAsList(futures);
            com.google.common.util.concurrent.ListenableFuture<ResponseListDto> guavaResult = Futures.transformAsync(collectedResults, mergeFunction);

            return FutureConverter.toSpringListenableFuture(guavaResult);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;

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
