package com.elections.counter.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class VotesByPrecinctResponse {

  private String precinct;
  private long votes;
}
