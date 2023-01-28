package com.elections.counter.document;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class Role {

  @MongoId
  private String id;

  @Getter
  private String name;
}
