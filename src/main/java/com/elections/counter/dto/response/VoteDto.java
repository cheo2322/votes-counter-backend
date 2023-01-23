package com.elections.counter.dto.response;

import com.elections.counter.document.DeskType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class VoteDto {

  private long votesAmount;
  private String parish;
  private String precinct;
  private int desk;
  private DeskType deskType;
}
