package com.elections.counter.service;

import com.elections.counter.document.DeskType;
import com.elections.counter.document.Precinct;
import com.elections.counter.dto.response.VotesByGenreResponse;
import com.elections.counter.dto.response.VotesByPrecinctResponse;
import com.elections.counter.repository.VoteRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

  @Autowired
  VoteRepository voteRepository;

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
}
