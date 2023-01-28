package com.elections.counter.document;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Getter
@Setter
@Builder
public class User {

  @MongoId
  private String userId;
  private String username;
  private String email;
  private String password;
  private Set<Role> roles;
}
