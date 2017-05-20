package com.khub.rest.spotify.adapter;

import com.khub.rest.dto.ResponseDto;
import com.khub.rest.spotify.model.Albums;
import com.khub.rest.spotify.model.Artist;
import com.khub.rest.spotify.model.Item;
import com.khub.rest.AppConstants;
import com.khub.rest.ResponseController;
import com.khub.rest.dto.ResponseListDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AlbumsResponseDtoAdapter extends ListenableFutureAdapter<ResponseListDto, ResponseEntity<Albums>>{
    private static final Logger log = LoggerFactory.getLogger(ResponseController.class);
    public static final String CLASSNAME = AlbumsResponseDtoAdapter.class.getName();

    public AlbumsResponseDtoAdapter (ListenableFuture<ResponseEntity<Albums>> albums){
        super(albums);
    }

    @Override
    protected ResponseListDto adapt(ResponseEntity<Albums> albumsResponseEntity) throws ExecutionException {
        String methodName = "adapt";
        log.info("Entering {} {}", new Object[] {CLASSNAME, methodName});
        Albums albums = albumsResponseEntity.getBody();
        List<ResponseDto> responseList = new ArrayList<ResponseDto>();
        for (Item item: albums.getAlbums().getItems()) {
            responseList.add(convertItemsItemToResponseDto(item));
        }
        log.info("Exiting {} {}", new Object[] {CLASSNAME, methodName});
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
