package com.elections.counter.service;

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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterService {

  @Autowired
  CandidateRepository candidateRepository;

  @Autowired
  VoteRepository voteRepository;

  public CandidateResponse createCandidate(CandidateRequest candidateRequest) {
    Candidate candidate = CandidateMapper.INSTANCE.requestToCandidate(candidateRequest);
    setCandidateCode(candidate);

    candidateRepository.save(candidate);

    return CandidateResponse.builder()
        .name(String.format("%s %s", candidateRequest.getName(), candidate.getLastName()))
        .build();
  }

  public VotesAddedResponse addVotesToCandidate(String id, VoteDto newVote) {
     return addVotes(newVote, candidateRepository.findById(id)
       .orElseThrow(() -> new RuntimeException("No candidate found!"))
     );
  }

  private VotesAddedResponse addVotes(VoteDto newVoteDto, Candidate candidate) {
    Vote newVote = VoteMapper.INSTANCE.dtoToVote(newVoteDto);

    return voteRepository
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
  }

  public List<CandidateDto> getAllCandidates() {
    return candidateRepository.findAll()
        .stream()
        .map(candidate -> {
          CandidateDto candidateDto = CandidateMapper.INSTANCE.candidateToResponse(candidate);
          candidateDto.setTotalVotes(0);

          candidateDto.setVotes(voteRepository
            .findByCandidateId(candidate.getCandidateId())
            .map(voteList -> voteList
              .stream()
              .map(vote -> {
                candidateDto.setTotalVotes(candidateDto.getTotalVotes() + vote.getVotesAmount());

                return VoteMapper.INSTANCE.voteToVoteDto(vote);
              })
              .collect(Collectors.toList()))
            .orElse(Collections.emptyList()));

          return candidateDto;
        })
        .collect(Collectors.toList());
  }

  private void setCandidateCode(Candidate candidate) {
    candidate.setCode(String.format("%s_%s_%s_%d", candidate.getLastName(), candidate.getName(),
        candidate.getPosition().name(), candidate.getList()));
  }
}
