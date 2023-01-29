package com.elections.counter.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorResponse {

  private int statusCode;
  private String message;
}
