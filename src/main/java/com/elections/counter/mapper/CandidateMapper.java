package com.elections.counter.mapper;

import com.elections.counter.document.Candidate;
import com.elections.counter.document.Votes;
import com.elections.counter.dto.request.CandidateRequest;
import com.elections.counter.dto.response.CandidateDto;
import com.elections.counter.dto.response.VotesDto;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

  CandidateMapper INSTANCE = Mappers.getMapper(CandidateMapper.class);

  @Named("votesToVotesDto")
  static List<VotesDto> votesToVotesDto(List<Votes> votes) {
    return votes.stream()
        .map(singleVotes ->
          VotesDto.builder()
          .votesAmount(singleVotes.getVotesAmount())
          .parish(singleVotes.getParish().getLabel())
          .build()
        ).collect(Collectors.toList());
  }

  @Mapping(source = "name", target = "name")
  Candidate requestToCandidate(CandidateRequest candidateRequest);

  @Mapping(source = "candidateId", target = "id")
  @Mapping(source = "position.label", target = "position")
  @Mapping(source = "votes", target = "votes", qualifiedByName = "votesToVotesDto")
  CandidateDto candidateToResponse(Candidate candidate);

  @Mapping(source = "parish", target = "parish")
  Votes dtoToVotes(VotesDto votesDto);
}
