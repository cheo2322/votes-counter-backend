package com.elections.counter.service;

import com.elections.counter.document.Candidate;
import com.elections.counter.document.Votes;
import com.elections.counter.dto.request.CandidateRequest;
import com.elections.counter.dto.response.CandidateDto;
import com.elections.counter.dto.response.CandidateResponse;
import com.elections.counter.dto.response.VotesAddedResponse;
import com.elections.counter.dto.response.VotesDto;
import com.elections.counter.mapper.CandidateMapper;
import com.elections.counter.repository.CandidateRepository;
import com.elections.counter.repository.VotesRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

  public VotesAddedResponse addVotesToCandidate(String id, VotesDto newVotes) {
     return candidateRepository.findById(id)
        .map(candidate -> addVotes(newVotes, candidate))
        .orElseThrow(() -> new RuntimeException("No candidate found!"));
  }

  private VotesAddedResponse addVotes(VotesDto newVotesDto, Candidate candidate) {
    Votes newVotes = CandidateMapper.INSTANCE.dtoToVotes(newVotesDto);

    if (!CollectionUtils.isEmpty(candidate.getVotes())) {
      return candidate.getVotes()
          .stream()
          .filter(votes -> votes.getParish().equals(newVotes.getParish()))
          .findFirst()
          .map(votes -> {
            long existentVotes = votes.getVotesAmount();
            long newVotesAmount = newVotes.getVotesAmount();

            long totalVotes = existentVotes + newVotesAmount;
            votes.setVotesAmount(totalVotes);

            votesRepository.save(votes);
            candidateRepository.save(candidate);

            return VotesAddedResponse.builder()
                .votesAdded(newVotesAmount)
                .totalVotesOnParish(totalVotes)
                .totalVotes(candidate.getVotes().stream().mapToLong(Votes::getVotesAmount).sum())
                .build();
          })
          .orElse(addVotesWithNoParishVotes(candidate, newVotes));

    } else {
      candidate.setVotes(Collections.singletonList(newVotes));

      votesRepository.save(newVotes);
      candidateRepository.save(candidate);

      return VotesAddedResponse.builder()
          .votesAdded(newVotes.getVotesAmount())
          .totalVotesOnParish(newVotes.getVotesAmount())
          .totalVotes(newVotes.getVotesAmount())
          .parish(newVotes.getParish())
          .build();
    }
  }

  private VotesAddedResponse addVotesWithNoParishVotes(Candidate candidate, Votes newVotes) {
    candidate.getVotes().add(newVotes);

    votesRepository.save(newVotes);
    candidateRepository.save(candidate);

    return VotesAddedResponse.builder()
        .votesAdded(newVotes.getVotesAmount())
        .totalVotesOnParish(newVotes.getVotesAmount())
        .totalVotes(candidate.getVotes().stream().mapToLong(Votes::getVotesAmount).sum())
        .parish(newVotes.getParish())
        .build();
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
