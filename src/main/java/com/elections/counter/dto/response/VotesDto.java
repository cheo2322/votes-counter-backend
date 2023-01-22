package com.elections.counter.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class VotesDto {

  private long votesAmount;
  private String parish;
}
