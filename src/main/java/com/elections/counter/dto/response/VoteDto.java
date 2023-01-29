package com.elections.counter.dto.response;

import com.elections.counter.document.enums.DeskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {

  private long votesAmount;
  private String parish;
  private String precinct;
  private int desk;
  private DeskType deskType;
}
