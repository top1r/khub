package com.khub.rest.spotify.adapter;

import com.khub.rest.AppConstants;
import com.khub.rest.dto.ResponseDto;
import com.khub.rest.dto.ResponseListDto;
import com.khub.rest.spotify.model.Albums;
import com.khub.rest.spotify.model.Artist;
import com.khub.rest.spotify.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AlbumsResponseDtoAdapter extends ListenableFutureAdapter<ResponseListDto, ResponseEntity<Albums>>{
    private static final Logger log = LoggerFactory.getLogger(AlbumsResponseDtoAdapter.class);
    private static final String CLASSNAME = AlbumsResponseDtoAdapter.class.getName();

    private Long startTime;

    public AlbumsResponseDtoAdapter (ListenableFuture<ResponseEntity<Albums>> albums, Long startTime){
        super(albums);
        this.startTime = startTime;
    }

    @Override
    protected ResponseListDto adapt(ResponseEntity<Albums> albumsResponseEntity) throws ExecutionException {
        String methodName = "adapt";
        log.debug("Entering {} {}", new Object[] {CLASSNAME, methodName});
        List<ResponseDto> responseList = null;
        try {
            Albums albums = albumsResponseEntity.getBody();
            if (null != albums && null != albums.getAlbums() && null != albums.getAlbums().getItems()) {
                responseList = new ArrayList<>();
                for (Item item: albums.getAlbums().getItems()) {
                    responseList.add(convertItemsItemToResponseDto(item));
                }
            }
        } catch (Exception e) {
            log.error("Error occurred during converting to DTO", e);
        }
        Long finishTime = System.currentTimeMillis();
        log.debug("Exiting {} {}", new Object[] {CLASSNAME, methodName});
        log.info("Finished Processing Spotify; total elapsed: {}, response code: {}", new Object[] {(finishTime - startTime) + " ms", albumsResponseEntity.getStatusCodeValue()});
        return new ResponseListDto(responseList);
    }

    private ResponseDto convertItemsItemToResponseDto (Item item) {
        ResponseDto ret = null;
        List<String> artistList;
        if (item.getArtists() != null && !item.getArtists().isEmpty()) {
            artistList = new ArrayList<>();
            for (Artist artist: item.getArtists()){
                artistList.add(artist.getName());
            }
            ret = new ResponseDto(item.getName(), artistList, AppConstants.ALBUM_TYPE);
        }
        return ret;
    }
}
