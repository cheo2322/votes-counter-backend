package com.elections.counter.service.impl;

import com.elections.counter.document.DeskType;
import com.elections.counter.document.Parish;
import com.elections.counter.document.Precinct;
import com.elections.counter.document.Vote;
import com.elections.counter.dto.response.VotesByGenreResponse;
import com.elections.counter.dto.response.VotesByParametersResponse;
import com.elections.counter.dto.response.vote.DeskVoteDto;
import com.elections.counter.dto.response.vote.PrecinctVoteDto;
import com.elections.counter.dto.response.vote.ParishVoteDto;
import com.elections.counter.repository.VoteRepository;
import com.elections.counter.service.VoteService;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
  public Set<ParishVoteDto> getVotesByDesk(String id) {
    return voteRepository.findByCandidateId(id)
      .map(this::getResponseFromVotes)
      .orElseGet(Collections::emptySet);
  }

  private Set<ParishVoteDto> getResponseFromVotes(List<Vote> votes) {
    Set<ParishVoteDto> response = new HashSet<>();

    for (Vote vote : votes) {
      ParishVoteDto parishVoteDto = getNewOrExistentParishVote(response, vote);

      Set<PrecinctVoteDto> votesByPrecinct = parishVoteDto.getVotesByPrecinct();
      if (!votesByPrecinct.isEmpty()) {
        PrecinctVoteDto precinctVoteDto = getNewOrExistentPrecinctVote(votesByPrecinct, vote);
        Set<DeskVoteDto> deskVoteDtos = new HashSet<>(precinctVoteDto.getVotesByDesk());

        DeskVoteDto deskVoteDto = getNewOrExistentDeskVote(deskVoteDtos, vote);
        deskVoteDto.setAmount(deskVoteDto.getAmount() + vote.getVotesAmount());

        deskVoteDtos.add(deskVoteDto);
        precinctVoteDto.setVotesByDesk(deskVoteDtos);

        Set<PrecinctVoteDto> newPrecinctDtos = new HashSet<>(votesByPrecinct);
        newPrecinctDtos.add(precinctVoteDto);

        parishVoteDto.setVotesByPrecinct(newPrecinctDtos);

      } else {
        response.add(newVoteResponse(vote));
      }
    }

    return response;
  }

  private ParishVoteDto newVoteResponse(Vote vote) {
    return ParishVoteDto.builder()
      .parish(vote.getParish().getLabel())
      .votesByPrecinct(Collections.singleton(
        PrecinctVoteDto.builder()
          .precinct(vote.getPrecinct().getLabel())
          .votesByDesk(Collections.singleton(
            DeskVoteDto.builder()
              .desk(vote.getDesk())
              .deskType(vote.getDeskType())
              .amount(vote.getVotesAmount())
              .build())
          ).build())
      ).build();
  }

  private ParishVoteDto getNewOrExistentParishVote(Set<ParishVoteDto> parishVotes, Vote vote) {
    return parishVotes
      .stream()
      .filter(parishVoteDto -> parishVoteDto.getParish().equals(vote.getParish().getLabel()))
      .findFirst()
      .orElse(ParishVoteDto.builder()
        .parish(vote.getParish().getLabel())
        .votesByPrecinct(Collections.emptySet())
        .build()
      );
  }

  private PrecinctVoteDto getNewOrExistentPrecinctVote(Set<PrecinctVoteDto> precinctVotes,
                                                       Vote vote) {

    return precinctVotes
      .stream()
      .filter(precinctVoteDto ->
        precinctVoteDto.getPrecinct().equals(vote.getPrecinct().getLabel()))
      .findFirst()
      .orElse(PrecinctVoteDto.builder()
        .precinct(vote.getPrecinct().getLabel())
        .votesByDesk(Collections.emptySet())
        .build()
      );
  }

  private DeskVoteDto getNewOrExistentDeskVote(Set<DeskVoteDto> deskVotes, Vote vote) {
    return deskVotes
      .stream()
      .filter(deskVoteDto -> deskVoteDto.getDesk() == vote.getDesk()
        && deskVoteDto.getDeskType().equals(vote.getDeskType()))
      .findFirst()
      .orElse(DeskVoteDto.builder()
        .desk(vote.getDesk())
        .deskType(vote.getDeskType())
        .amount(0)
        .build()
      );
  }
}
