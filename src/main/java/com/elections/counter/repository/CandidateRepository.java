package com.elections.counter.repository;

import com.elections.counter.document.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CandidateRepository extends MongoRepository<Candidate, String> {

}
