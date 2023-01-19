package com.elections.counter.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Candidate {

  @Id
  private String candidateId;

  @Indexed(unique = true)
  private String code;
  private String name;
  private String lastName;
  private int list;
  private String city;
  private String province;
  private long votes;
  private Position position;
  public enum Position {
    ALCALDE,
    PREFECTO,
    CONCEJAL,
  }
}
