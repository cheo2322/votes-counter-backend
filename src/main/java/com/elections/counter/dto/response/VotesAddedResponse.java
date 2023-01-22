package com.elections.counter.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VotesAddedResponse {

  private long votesAdded;
  private long totalVotesOnParish;
  private long totalVotesOnDesk;
  private long totalVotes;
  private String parish;
  private String precinct;
  private int desk;
}
