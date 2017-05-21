package com.khub.rest.spotify.service;

import com.khub.rest.AppConstants;
import com.khub.rest.ResponseController;
import com.khub.rest.dto.ResponseListDto;
import com.khub.rest.spotify.adapter.AlbumsResponseDtoAdapter;
import com.khub.rest.spotify.model.Albums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

@Service
public class SpotifyServiceImpl implements SpotifyService {
    private static final Logger log = LoggerFactory.getLogger(ResponseController.class);
    private static final String CLASSNAME = SpotifyServiceImpl.class.getName();

    @Value( "${spotify.limit}" ) private String limit;
    @Value( "${spotify.baseurl}") private String baseURL;
    private String URL = "search?q={query}&type=album&limit={limit}";

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Override
    public ListenableFuture<ResponseListDto> search(String query) {
        String methodName = "search";
        log.debug("Entering {} {}", new Object[] {CLASSNAME, methodName});
        if (StringUtils.isEmpty(limit)){
            limit = AppConstants.DEFAULT_QUERY_LIMIT;
        }
        ListenableFuture<ResponseEntity<Albums>> result = null;
        Long startTime = System.currentTimeMillis();
        try {
            result = asyncRestTemplate.getForEntity(baseURL + URL, Albums.class, query, limit);
            log.info("Sent request to Spotify; query: {}, limit: {}", new Object[] {query, limit});

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("Exiting {} {}", new Object[] {CLASSNAME, methodName});
        return new AlbumsResponseDtoAdapter(result, startTime);
    }

}
