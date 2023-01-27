package com.elections.counter.dto.response;

import com.elections.counter.document.enums.DeskType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VotesAddedResponse {

  private long votesAdded;
  private long totalVotesOnDesk;
  private String parish;
  private String precinct;
  private int desk;
  private DeskType deskType;
}
