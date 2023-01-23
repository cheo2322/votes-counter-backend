package com.elections.counter.service;

import com.elections.counter.dto.request.CandidateRequest;
import com.elections.counter.dto.response.CandidateDto;
import com.elections.counter.dto.response.CandidateResponse;
import com.elections.counter.dto.response.VoteDto;
import com.elections.counter.dto.response.VotesAddedResponse;
import java.util.List;

public interface CandidateService {

  CandidateResponse createCandidate(CandidateRequest candidateRequest);

  VotesAddedResponse addVotesToCandidate(String id, VoteDto newVote);

  List<CandidateDto> getAllCandidates();
}
