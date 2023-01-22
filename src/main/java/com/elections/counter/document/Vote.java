package com.elections.counter.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Vote {

  @Id
  private String voteId;
  private String candidateId;
  private long votesAmount;
  private Parish parish;
  private Precinct precinct;
  private int desk;
}
