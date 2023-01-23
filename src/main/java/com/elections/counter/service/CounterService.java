package com.elections.counter.service;

import com.elections.counter.document.Candidate;
import com.elections.counter.document.DeskType;
import com.elections.counter.document.Precinct;
import com.elections.counter.document.Vote;
import com.elections.counter.dto.request.CandidateRequest;
import com.elections.counter.dto.response.CandidateDto;
import com.elections.counter.dto.response.CandidateResponse;
import com.elections.counter.dto.response.VoteDto;
import com.elections.counter.dto.response.VotesAddedResponse;
import com.elections.counter.dto.response.VotesByGenreResponse;
import com.elections.counter.dto.response.VotesByPrecinctResponse;
import com.elections.counter.mapper.CandidateMapper;
import com.elections.counter.mapper.VoteMapper;
import com.elections.counter.repository.CandidateRepository;
import com.elections.counter.repository.VoteRepository;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  public VotesByGenreResponse getVotesByGenre(String id) {
    return voteRepository.findByCandidateId(id)
      .map(votes -> {
        VotesByGenreResponse response = VotesByGenreResponse.builder()
          .female(0L)
          .male(0L)
          .build();

        votes.forEach(vote -> {
          if (vote.getDeskType().equals(DeskType.FEMENINO)) {
            response.setFemale(response.getFemale() + vote.getVotesAmount());
          } else if (vote.getDeskType().equals(DeskType.MASCULINO)){
            response.setMale(response.getMale() + vote.getVotesAmount());
          } else {
            throw new RuntimeException("Something went wrong!");
          }
        });

        return response;
        })
      .orElseThrow(() -> new RuntimeException("Votes not found!"));
  }

  public List<VotesByPrecinctResponse> getVotesByPrecinct(String id) {
    return voteRepository.findByCandidateId(id)
      .map(votes -> {
        Map<Precinct, Long> votesMap = new HashMap<>();

        votes.forEach(vote ->
          votesMap.put(vote.getPrecinct(), votesMap.containsKey(vote.getPrecinct())
            ? votesMap.get(vote.getPrecinct()) + vote.getVotesAmount()
            : vote.getVotesAmount()));

        return votesMap;
      })
      .orElseThrow(() -> new RuntimeException("Votes not found!"))
      .entrySet().stream()
      .map(precinctLongEntry ->
        VotesByPrecinctResponse.builder()
        .precinct(precinctLongEntry.getKey().getLabel())
        .votes(precinctLongEntry.getValue())
        .build()
      ).collect(Collectors.toList());
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

  private void setCandidateCode(Candidate candidate) {
    candidate.setCode(String.format("%s_%s_%s_%d", candidate.getLastName(), candidate.getName(),
        candidate.getPosition().name(), candidate.getList()));
  }
}
