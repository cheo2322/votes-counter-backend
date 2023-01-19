package com.elections.counter.service;

import com.elections.counter.document.Candidate;
import com.elections.counter.dto.request.CandidateRequest;
import com.elections.counter.dto.response.CandidateResponse;
import com.elections.counter.mapper.CandidateMapper;
import com.elections.counter.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterService {

  @Autowired
  CandidateRepository candidateRepository;

  public CandidateResponse createCandidate(CandidateRequest candidateRequest) {
    Candidate candidate = CandidateMapper.INSTANCE.requestToCandidate(candidateRequest);
    setCandidateCode(candidate);

    candidateRepository.save(candidate);

    return CandidateResponse.builder().name(candidateRequest.getName()).build();
  }

  public long addVotes(String id, long votes) {
    return candidateRepository.findById(id)
        .map(candidate -> {
          long updatedVotes = candidate.getVotes() + votes;
          candidate.setVotes(updatedVotes);

          candidateRepository.save(candidate);
          return candidate.getVotes();
        })
        .orElseThrow(() -> new RuntimeException("Not found."));
  }

  private void setCandidateCode(Candidate candidate) {
    candidate.setCode(String.format("%s_%s_%s_%d", candidate.getLastName(),
        candidate.getPosition().name(), candidate.getCity(), candidate.getList()));
  }
}
