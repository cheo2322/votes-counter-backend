package com.elections.counter.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateDto {

  private String id;
  private String name;
  private String lastName;
  private int list;
  private long totalVotes;
  private String position;
}
