package com.elections.counter.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Votes {

  @Id
  private String voteId;

  private long votesAmount;
  private Parish parish;

  public enum Parish {
    SAN_MIGUEL_DE_URCUQUI("San Miguel de Urcuquí"),
    PABLO_ARENAS("Pablo Arenas"),
    CAHUASQUI("Cahuasquí"),
    BUENOS_AIRES("Buenos Aires"),
    SAN_BLAS("San Blass"),
    TUMBABIRO("Tumbabiro");

    private final String label;

    Parish(String label) {
      this.label = label;
    }

    public String getLabel() {
      return label;
    }
  }
}
