package com.elections.counter.document;

import com.elections.counter.document.enums.DeskType;
import com.elections.counter.document.enums.Parish;
import com.elections.counter.document.enums.Precinct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@CompoundIndex(def =
  "{'candidateId': 1, 'parish': 1, 'precinct': 1, 'desk': 1, 'deskType': 1}, unique = true")
public class Vote {

  @Id
  private String voteId;
  private String candidateId;
  private long votesAmount;
  private Parish parish;
  private Precinct precinct;
  private int desk;
  private DeskType deskType;
}
