package com.elections.counter.dto.response.vote;

import com.elections.counter.document.DeskType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class DeskVoteDto {

  private int desk;
  private DeskType deskType;
  @Setter
  private long amount;
}
