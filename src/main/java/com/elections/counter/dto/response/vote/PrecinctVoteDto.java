package com.elections.counter.dto.response.vote;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PrecinctVoteDto {

  private String precinct;
  private Set<DeskVoteDto> votesByDesk;
}
