package com.elections.counter.handler;

import com.elections.counter.handler.exception.CandidateAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CounterExceptionHandler {

  @ExceptionHandler(CandidateAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponse handleCandidateAlreadyExistsException(CandidateAlreadyExistsException e) {
    return ErrorResponse.builder()
        .statusCode(HttpStatus.CONFLICT.value())
        .message(e.getMessage())
        .build();
  }
}
