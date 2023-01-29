package com.elections.counter.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CandidateDto {

  private String id;
  private String name;
  private String lastName;
  private long totalVotes;
  private String position;
}
