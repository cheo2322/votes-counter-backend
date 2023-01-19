package com.elections.counter.dto.response;

import com.elections.counter.document.Candidate.Position;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateDto {

  private String name;
  private String lastName;
  private int list;
  private String city;
  private String province;
  private long votes;
  private Position position;

}