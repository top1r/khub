package com.khub.rest.google.books.adapter;

import com.khub.rest.dto.ResponseDto;
import com.khub.rest.google.books.model.ItemsItem;
import com.khub.rest.AppConstants;
import com.khub.rest.ResponseController;
import com.khub.rest.dto.ResponseListDto;
import com.khub.rest.google.books.model.Books;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BooksResponseDtoAdapter extends ListenableFutureAdapter<ResponseListDto, ResponseEntity<Books>> {

    private static final Logger log = LoggerFactory.getLogger(ResponseController.class);
    public static final String CLASSNAME = BooksResponseDtoAdapter.class.getName();

    public BooksResponseDtoAdapter (ListenableFuture<ResponseEntity<Books>> books){
        super(books);
    }

    @Override
    protected ResponseListDto adapt(ResponseEntity<Books> booksResponseEntity) throws ExecutionException {
        String methodName = "adapt";
        log.info("Entering {} {}", new Object[] {CLASSNAME, methodName});
        Books books = booksResponseEntity.getBody();
        List<ResponseDto> responseList = new ArrayList<ResponseDto>();
        for (ItemsItem item: books.getItems()) {
            responseList.add(convertItemsItemToResponseDto(item));
        }
        log.info("Exiting {} {}", new Object[] {CLASSNAME, methodName});
        return new ResponseListDto(responseList);
    }

    private ResponseDto convertItemsItemToResponseDto (ItemsItem item) {
        return (item.getVolumeInfo() != null) ? new ResponseDto(item.getVolumeInfo().getTitle(), item.getVolumeInfo().getAuthors(), AppConstants.BOOK_TYPE) : null;
    }
}
