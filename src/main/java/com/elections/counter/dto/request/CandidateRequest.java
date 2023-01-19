package com.elections.counter.dto.request;

import com.elections.counter.document.Candidate.Position;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CandidateRequest {

  private String name;
  private String lastName;
  private int list;
  private String city;
  private String province;
  private Position position;
}
