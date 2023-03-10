package com.elections.counter.repository;

import com.elections.counter.document.enums.DeskType;
import com.elections.counter.document.enums.Parish;
import com.elections.counter.document.enums.Precinct;
import com.elections.counter.document.Vote;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {

    Optional<List<Vote>> findByCandidateId(String candidateId);

    Optional<Vote> findByCandidateIdAndParishAndPrecinctAndDeskAndDeskType(
      String candidateId, Parish parish, Precinct precinct, int desk, DeskType deskType);
}
