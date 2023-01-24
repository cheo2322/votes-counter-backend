package com.elections.counter.service.impl;

import com.elections.counter.document.Candidate;
import com.elections.counter.document.Vote;
import com.elections.counter.dto.request.CandidateRequest;
import com.elections.counter.dto.response.CandidateDto;
import com.elections.counter.dto.response.CandidateResponse;
import com.elections.counter.dto.response.VoteDto;
import com.elections.counter.dto.response.VotesAddedResponse;
import com.elections.counter.mapper.CandidateMapper;
import com.elections.counter.mapper.VoteMapper;
import com.elections.counter.repository.CandidateRepository;
import com.elections.counter.repository.VoteRepository;
import com.elections.counter.service.CandidateService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {

  @Autowired
  CandidateRepository candidateRepository;

  @Autowired
  VoteRepository voteRepository;

  @Override
  public CandidateResponse createCandidate(CandidateRequest candidateRequest) {
    Candidate candidate = CandidateMapper.INSTANCE.requestToCandidate(candidateRequest);
    setCandidateCode(candidate);

    candidateRepository.save(candidate);

    return CandidateResponse.builder()
      .name(String.format("%s %s", candidateRequest.getName(), candidate.getLastName()))
      .build();
  }

  @Override
  public VotesAddedResponse addVotesToCandidate(String id, VoteDto newVote) {
     return addVotes(newVote, candidateRepository.findById(id)
       .orElseThrow(() -> new RuntimeException("No candidate found!"))
     );
  }

  @Override
  public List<CandidateDto> getAllCandidates() {
    return candidateRepository.findAll()
      .stream()
      .map(CandidateMapper.INSTANCE::candidateToResponse)
      .collect(Collectors.toList());
  }

  private VotesAddedResponse addVotes(VoteDto newVoteDto, Candidate candidate) {
    Vote newVote = VoteMapper.INSTANCE.dtoToVote(newVoteDto);
    candidate.setTotalVotes(candidate.getTotalVotes() + newVote.getVotesAmount());

    VotesAddedResponse response = voteRepository
      .findByCandidateIdAndParishAndPrecinctAndDeskAndDeskType(candidate.getCandidateId(),
        newVote.getParish(), newVote.getPrecinct(), newVote.getDesk(), newVote.getDeskType())
      .map(vote -> {
        vote.setVotesAmount(vote.getVotesAmount() + newVote.getVotesAmount());
        voteRepository.save(vote);

        return VoteMapper.INSTANCE.voteToVotesAddedResponse(vote, newVote.getVotesAmount());
      })
      .orElseGet(() -> {
        newVote.setCandidateId(candidate.getCandidateId());
        voteRepository.save(newVote);

        return VoteMapper.INSTANCE.voteToVotesAddedResponse(newVote, newVote.getVotesAmount());
      });

    candidateRepository.save(candidate);

    return response;
  }

  private void setCandidateCode(Candidate candidate) {
    candidate.setCode(String.format("%s_%s_%s_%d", candidate.getLastName(), candidate.getName(),
        candidate.getPosition().name(), candidate.getList()));
  }
}
