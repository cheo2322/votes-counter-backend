package com.elections.counter.dto.response;

import com.elections.counter.document.Votes.Parish;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VotesAddedResponse {

  private long votesAdded;
  private long totalVotesOnParish;
  private long totalVotes;
  private Parish parish;
}
