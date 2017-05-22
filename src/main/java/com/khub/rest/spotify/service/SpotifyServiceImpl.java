package com.khub.rest.spotify.service;

import com.khub.rest.AppConstants;
import com.khub.rest.ResponseController;
import com.khub.rest.dto.ResponseListDto;
import com.khub.rest.spotify.adapter.AlbumsResponseDtoAdapter;
import com.khub.rest.spotify.model.Albums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import static com.khub.rest.helpers.LoggingHelper.*;

@Service
public class SpotifyServiceImpl implements SpotifyService {
    private static final Logger log = LoggerFactory.getLogger(ResponseController.class);
    private static final String CLASSNAME = SpotifyServiceImpl.class.getName();

    @Value( "${spotify.limit}" ) private String limit;
    @Value( "${spotify.baseurl}") private String baseURL;
    private String URL = "search?q={query}&type=album&limit={limit}";

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Qualifier("gaugeService")
    @Autowired
    private GaugeService gaugeService;

    @Override
    public ListenableFuture<ResponseListDto> search(String query) {
        String methodName = "search";
        entering(log, CLASSNAME, methodName);

        if (StringUtils.isEmpty(limit)){
            limit = AppConstants.DEFAULT_QUERY_LIMIT;
        }
        ListenableFuture<ResponseEntity<Albums>> result = null;
        Long startTime = System.currentTimeMillis();
        try {
            result = asyncRestTemplate.getForEntity(baseURL + URL, Albums.class, query, limit);
            logSentStatistic(log, AppConstants.SPOTIFY_SERVICE_NAME, query, limit);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        exiting(log, CLASSNAME, methodName);
        return new AlbumsResponseDtoAdapter(result, startTime, gaugeService);
    }

}
