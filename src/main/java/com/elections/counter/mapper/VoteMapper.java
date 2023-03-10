package com.elections.counter.mapper;

import com.elections.counter.document.Vote;
import com.elections.counter.dto.response.VoteDto;
import com.elections.counter.dto.response.VotesAddedResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VoteMapper {

  VoteMapper INSTANCE = Mappers.getMapper(VoteMapper.class);

  @Mapping(source = "parish.label", target = "parish")
  @Mapping(source = "precinct.label", target = "precinct")
  VoteDto voteToVoteDto(Vote vote);

  Vote dtoToVote(VoteDto voteDto);

  @Mapping(source = "vote.votesAmount", target = "totalVotesOnDesk")
  VotesAddedResponse voteToVotesAddedResponse(Vote vote, long votesAdded);
}
