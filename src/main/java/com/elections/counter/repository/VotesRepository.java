package com.elections.counter.repository;

import com.elections.counter.document.Votes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotesRepository extends MongoRepository<Votes, String> {

}
