package com.elections.counter.service;

import com.elections.counter.dto.response.VoteDto;
import com.elections.counter.dto.response.VotesByGenreResponse;
import com.elections.counter.dto.response.VotesByParametersResponse;
import java.util.List;

public interface VoteService {

  VotesByGenreResponse getVotesByGenre(String id);

  List<VotesByParametersResponse> getVotesByPrecinct(String id);

  List<VotesByParametersResponse> getVotesByParish(String id);

  List<VoteDto> getVotesByDesk(String id);
}
