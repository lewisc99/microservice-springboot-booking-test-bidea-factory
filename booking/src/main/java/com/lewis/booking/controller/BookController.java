package com.lewis.booking.controller;

import com.lewis.booking.config.ConvertTo;
import com.lewis.booking.config.InvalidDiscountException;
import com.lewis.booking.domain.entities.Book;
import com.lewis.booking.domain.request.BookRequest;
import com.lewis.booking.domain.response.BookResponse;
import com.lewis.booking.domain.response.ErrorResponse;
import com.lewis.booking.service.contracts.BookService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping(value = "book")
@OpenAPIDefinition(info = @Info(title = "Book", version = "1.0.0", description = "SpringBoot Server Dev"))
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private ConvertTo convertTo;


    @PostMapping
    @RateLimiter(name="default")
    @Retry(name = "sample-api")
    @ApiOperation(value="create a new Booking",
            notes = "will create a new Booking, you must add valid properties",
            response = BookResponse.class, code = 200
    )
    public ResponseEntity<BookResponse> create(@RequestBody @Valid BookRequest bookRequest)  {
        Book book =  convertTo.Book(bookRequest);
        BookResponse result = bookService.create(book);
        return ResponseEntity.ok(result);
    }
}
