package com.elections.counter.dto.request;

import com.elections.counter.document.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CandidateRequest {

  private String name;
  private String lastName;
  private int list;
  private Position position;
}
