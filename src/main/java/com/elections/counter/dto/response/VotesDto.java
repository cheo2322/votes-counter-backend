package com.elections.counter.dto.response;

import com.elections.counter.document.Votes.Parish;
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
  private Parish parish;
}
