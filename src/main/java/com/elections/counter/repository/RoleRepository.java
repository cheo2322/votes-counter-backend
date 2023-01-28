package com.elections.counter.repository;

import com.elections.counter.document.Role;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

  Optional<Role> findByName(String name);
}
