package com.elections.counter.mapper;

import com.elections.counter.document.Candidate;
import com.elections.counter.document.Votes;
import com.elections.counter.dto.request.CandidateRequest;
import com.elections.counter.dto.response.CandidateDto;
import com.elections.counter.dto.response.VotesDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

  CandidateMapper INSTANCE = Mappers.getMapper(CandidateMapper.class);

  @Mapping(source = "name", target = "name")
  Candidate requestToCandidate(CandidateRequest candidateRequest);

  @Mapping(source = "candidateId", target = "id")
  @Mapping(source = "position.label", target = "position")
  @Mapping(source = "votes", target = "votes", qualifiedByName = "votesToVotesDto")
  CandidateDto candidateToResponse(Candidate candidate);

  @Named("votesToVotesDto")
  List<VotesDto> votesToVotesDto(List<Votes> votes);
}
