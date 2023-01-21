package com.elections.counter.document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@ToString
public class Candidate {

  @Id
  private String candidateId;

  @Indexed(unique = true)
  private String code;
  private String name;
  private String lastName;
  private int list;
  private long votes;
  private Position position;

  public enum Position {
    ALCALDE("Alcalde"),
    PREFECTO("Prefecto"),
    CONCEJAL("Concejal");

    private final String label;
    Position(String label){
      this.label = label;
    }

    public String getLabel() {
      return label;
    }
  }
}
