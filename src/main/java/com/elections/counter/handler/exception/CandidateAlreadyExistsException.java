package com.elections.counter.handler.exception;

public class CandidateAlreadyExistsException extends RuntimeException {

  private String message;

  public CandidateAlreadyExistsException(String message) {
    super(message);
    this.message = message;
  }
}
