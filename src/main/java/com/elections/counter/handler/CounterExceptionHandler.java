package com.elections.counter.handler;

import com.elections.counter.handler.exception.CandidateAlreadyExistsException;
import com.elections.counter.handler.exception.CandidateNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CounterExceptionHandler {

  @ExceptionHandler(CandidateAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponse handleCandidateAlreadyExistsException(CandidateAlreadyExistsException e) {
    return ErrorResponse.builder()
        .statusCode(HttpStatus.CONFLICT.value())
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(CandidateNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleCandidateNotFoundException(CandidateNotFoundException e) {
    return ErrorResponse.builder()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleError(Exception e) {
    log.error(e.toString());

    return ErrorResponse.builder()
        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message("Error server!")
        .build();
  }
}
