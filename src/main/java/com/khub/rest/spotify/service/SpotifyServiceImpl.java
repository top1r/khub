package com.khub.rest.spotify.service;

import com.google.common.base.Stopwatch;
import com.khub.rest.spotify.model.Albums;
import com.khub.rest.AppConstants;
import com.khub.rest.ResponseController;
import com.khub.rest.dto.ResponseListDto;
import com.khub.rest.spotify.adapter.AlbumsResponseDtoAdapter;
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
public class SpotifyServiceImpl implements SpotifyService {
    private static final Logger log = LoggerFactory.getLogger(ResponseController.class);
    private static final String URL = "https://api.spotify.com/v1/search?q={query}&type=album&limit={limit}";
    public static final String CLASSNAME = SpotifyServiceImpl.class.getName();

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Async
    @Override
    public ListenableFuture<ResponseListDto> search(String query) {
        return search(query, null);
    }

    @Async
    public ListenableFuture<ResponseListDto> search(String query, String limit) {
        String methodName = "search";
        log.info("Entering {} {}", new Object[] {CLASSNAME, methodName});
        if (StringUtils.isEmpty(limit)) {
            limit = String.valueOf(AppConstants.DEFAULT_QUERY_LIMIT);
        }
        ListenableFuture<ResponseEntity<Albums>> result = null;
        try {
            Stopwatch stopwatch = Stopwatch.createStarted();
            result = asyncRestTemplate.getForEntity(URL, Albums.class, query, limit);
            stopwatch.stop();
            log.info("Sent request to Spotify; query: {}, limit: {}, elapsed: {}", new Object[] {query, limit, stopwatch});

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("Exiting {} {}", new Object[] {CLASSNAME, methodName});
        return new AlbumsResponseDtoAdapter(result);
    }

}
