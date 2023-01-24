package com.elections.counter.service.impl;

import com.elections.counter.document.DeskType;
import com.elections.counter.document.Parish;
import com.elections.counter.document.Precinct;
import com.elections.counter.dto.response.VoteDto;
import com.elections.counter.dto.response.VotesByGenreResponse;
import com.elections.counter.dto.response.VotesByParametersResponse;
import com.elections.counter.mapper.VoteMapper;
import com.elections.counter.repository.VoteRepository;
import com.elections.counter.service.VoteService;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {

  @Autowired
  VoteRepository voteRepository;

  @Override
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

  @Override
  public List<VotesByParametersResponse> getVotesByPrecinct(String id) {
    return voteRepository.findByCandidateId(id)
      .map(votes -> {
        Map<Precinct, Long> votesMap = new HashMap<>();

        votes.forEach(vote ->
          votesMap.put(vote.getPrecinct(), votesMap.containsKey(vote.getPrecinct())
            ? votesMap.get(vote.getPrecinct()) + vote.getVotesAmount()
            : vote.getVotesAmount()
          )
        );

        return votesMap;
      })
      .orElseThrow(() -> new RuntimeException("Votes not found!"))
      .entrySet().stream()
      .map(precinct ->
        VotesByParametersResponse.builder()
          .name(precinct.getKey().getLabel())
          .value(precinct.getValue())
          .build()
      ).collect(Collectors.toList());
  }

  @Override
  public List<VotesByParametersResponse> getVotesByParish(String id) {
    return voteRepository.findByCandidateId(id)
      .map(votes -> {
        Map<Parish, Long> votesMap = new HashMap<>();

        votes.forEach(vote ->
          votesMap.put(vote.getParish(), votesMap.containsKey(vote.getParish())
            ? votesMap.get(vote.getParish()) + vote.getVotesAmount()
            : vote.getVotesAmount()
          )
        );

        return votesMap;
      })
      .orElseThrow(() -> new RuntimeException("Votes not found!"))
      .entrySet().stream()
      .map(parish ->
        VotesByParametersResponse.builder()
          .name(parish.getKey().getLabel())
          .value(parish.getValue())
          .build()
      ).collect(Collectors.toList());
  }

  @Override
  public List<VoteDto> getVotesByDesk(String id) {
    return voteRepository.findByCandidateId(id)
      .map(votes -> votes.stream()
        .map(VoteMapper.INSTANCE::voteToVoteDto)
        .collect(Collectors.toList()))
      .orElseGet(Collections::emptyList);
  }
}
