package com.elections.counter.handler.exception;

public class CandidateNotFoundException extends RuntimeException {

  private String message;
  
  public CandidateNotFoundException(String message) {
    super(message);
    this.message = message;
  }
}
