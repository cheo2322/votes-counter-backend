package com.elections.counter.service;

import com.elections.counter.document.Candidate;
import com.elections.counter.dto.request.CandidateRequest;
import com.elections.counter.dto.response.CandidateDto;
import com.elections.counter.dto.response.CandidateResponse;
import com.elections.counter.dto.response.VotesDto;
import com.elections.counter.mapper.CandidateMapper;
import com.elections.counter.repository.CandidateRepository;
import com.elections.counter.repository.VotesRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterService {

  @Autowired
  CandidateRepository candidateRepository;

  @Autowired
  VotesRepository votesRepository;

  public CandidateResponse createCandidate(CandidateRequest candidateRequest) {
    Candidate candidate = CandidateMapper.INSTANCE.requestToCandidate(candidateRequest);
    setCandidateCode(candidate);

    candidateRepository.save(candidate);

    return CandidateResponse.builder()
        .name(String.format("%s %s", candidateRequest.getName(), candidate.getLastName()))
        .build();
  }

  public long addVotes(String id, VotesDto newVotes) {
    return candidateRepository.findById(id)
        .map(candidate -> candidate.getVotes()
            .stream()
            .filter(votes1 -> votes1.getParish().equals(newVotes.getParish()))
            .findFirst()
            .map(votes -> {
              long newVotesAmount = newVotes.getVotesAmount();
              votes.setVotesAmount(votes.getVotesAmount() + newVotesAmount);
              votesRepository.save(votes);

              candidate.setTotalVotes(candidate.getTotalVotes() + newVotesAmount);
              candidateRepository.save(candidate);

              return votes.getVotesAmount();
            })
            .orElseThrow(() -> new RuntimeException("No votes found!"))
        )
        .orElseThrow(() -> new RuntimeException("No candidate found!"));
  }

  public List<CandidateDto> getAllCandidates() {
    return candidateRepository.findAll()
        .stream().map(CandidateMapper.INSTANCE::candidateToResponse)
        .collect(Collectors.toList());
  }

  private void setCandidateCode(Candidate candidate) {
    candidate.setCode(String.format("%s_%s_%s_%d", candidate.getLastName(), candidate.getName(),
        candidate.getPosition().name(), candidate.getList()));
  }
}
