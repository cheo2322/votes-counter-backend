package com.elections.counter.mapper;

import com.elections.counter.document.Candidate;
import com.elections.counter.dto.request.CandidateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

  CandidateMapper INSTANCE = Mappers.getMapper(CandidateMapper.class);

  @Mapping(source = "name", target = "name")
  Candidate requestToCandidate(CandidateRequest candidateRequest);
}
