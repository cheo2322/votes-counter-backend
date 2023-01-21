package com.elections.counter.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateDto {

  private String id;
  private String name;
  private String lastName;
  private int list;
  private List<VotesDto> votes;
  private long totalVotes;
  private String position;
}
