package com.khub.rest.google.books.adapter;

import com.khub.rest.AppConstants;
import com.khub.rest.dto.ResponseDto;
import com.khub.rest.dto.ResponseListDto;
import com.khub.rest.google.books.model.Books;
import com.khub.rest.google.books.model.ItemsItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.khub.rest.helpers.LoggingHelper.*;

public class BooksResponseDtoAdapter extends ListenableFutureAdapter<ResponseListDto, ResponseEntity<Books>> {

    private static final String CLASSNAME = BooksResponseDtoAdapter.class.getName();
    private static final Logger log = LoggerFactory.getLogger(BooksResponseDtoAdapter.class);

    private Long startTime;
    private GaugeService gaugeService;

    public BooksResponseDtoAdapter (ListenableFuture<ResponseEntity<Books>> books, Long startTime, GaugeService gaugeService){
        super(books);
        this.startTime = startTime;
        this.gaugeService = gaugeService;
    }

    @Override
    protected ResponseListDto adapt(ResponseEntity<Books> booksResponseEntity) throws ExecutionException {
        String methodName = "adapt";
        entering(log, CLASSNAME, methodName);
        List<ResponseDto> responseList = null;
        try {
            Books books = booksResponseEntity.getBody();
            if (null != books && null != books.getItems()) {
                responseList = new ArrayList<>();
                for (ItemsItem item : books.getItems()) {
                    responseList.add(convertItemsItemToResponseDto(item));
                }
            }
        } catch (Exception e) {
            log.error("Error occurred during converting to DTO ", e);
        }
        Long totalTime = System.currentTimeMillis() - startTime;
        exiting(log, CLASSNAME, methodName);
        logTotalStatistic(log, AppConstants.GOOGLEBOOKS_SERVICE_NAME, totalTime, booksResponseEntity.getStatusCodeValue());
        gaugeService.submit(AppConstants.GOOGLEBOOKS_RESPONSE_LAST, totalTime);
        return new ResponseListDto(responseList);
    }

    private ResponseDto convertItemsItemToResponseDto (ItemsItem item) {
        return (item.getVolumeInfo() != null) ? new ResponseDto(item.getVolumeInfo().getTitle(), item.getVolumeInfo().getAuthors(), AppConstants.BOOK_TYPE) : null;
    }
}
