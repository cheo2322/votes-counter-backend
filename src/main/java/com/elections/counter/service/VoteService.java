package com.elections.counter.service;

import com.elections.counter.dto.response.VotesByGenreResponse;
import com.elections.counter.dto.response.VotesByParametersResponse;
import com.elections.counter.dto.response.vote.ParishVoteDto;
import java.util.List;
import java.util.Set;

public interface VoteService {

  VotesByGenreResponse getVotesByGenre(String id);

  List<VotesByParametersResponse> getVotesByPrecinct(String id);

  List<VotesByParametersResponse> getVotesByParish(String id);

  Set<ParishVoteDto> getVotesByDesk(String id);
}
