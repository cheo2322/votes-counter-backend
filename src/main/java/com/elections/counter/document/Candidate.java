package com.elections.counter.document;

import com.elections.counter.document.enums.Position;
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
  private Position position;
  private long totalVotes;
}
