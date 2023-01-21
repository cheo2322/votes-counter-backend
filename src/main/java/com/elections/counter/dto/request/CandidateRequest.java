package com.elections.counter.dto.request;

import com.elections.counter.document.Candidate.Parish;
import com.elections.counter.document.Candidate.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateRequest {

  private String name;
  private String lastName;
  private int list;
  private Parish parish;
  private Position position;
}
