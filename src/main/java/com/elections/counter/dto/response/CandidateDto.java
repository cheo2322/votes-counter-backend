package com.elections.counter.dto.response;

import com.elections.counter.document.Candidate.Position;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateDto {

  private String id;
  private String name;
  private String lastName;
  private int list;
  private long votes;
  private String position;

}
