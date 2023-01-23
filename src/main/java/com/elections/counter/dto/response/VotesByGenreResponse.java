package com.elections.counter.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VotesByGenreResponse {

  private long male;
  private long female;
}
