package com.elections.counter.service;

import com.elections.counter.dto.response.VotesByGenreResponse;
import com.elections.counter.dto.response.VotesByPrecinctResponse;
import java.util.List;

public interface VoteService {

  VotesByGenreResponse getVotesByGenre(String id);

  List<VotesByPrecinctResponse> getVotesByPrecinct(String id);
}
