package com.elections.counter.mapper;

import com.elections.counter.document.Candidate;
import com.elections.counter.dto.request.CandidateRequest;
import com.elections.counter.dto.response.CandidateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

  CandidateMapper INSTANCE = Mappers.getMapper(CandidateMapper.class);

  @Mapping(target = "votes", constant = "0L")
  Candidate requestToCandidate(CandidateRequest candidateRequest);

  @Mapping(source = "candidateId", target = "id")
  @Mapping(source = "position.label", target = "position")
  CandidateDto candidateToResponse(Candidate candidate);
}
