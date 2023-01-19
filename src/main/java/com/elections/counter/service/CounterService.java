package com.elections.counter.service;

import com.elections.counter.dto.request.CandidateRequest;
import com.elections.counter.dto.response.CandidateResponse;
import com.elections.counter.mapper.CandidateMapper;
import com.elections.counter.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterService {

  @Autowired
  CandidateRepository candidateRepository;

  public CandidateResponse createCandidate(CandidateRequest candidateRequest) {
   candidateRepository.save(CandidateMapper.INSTANCE.requestToCandidate(candidateRequest));

    return CandidateResponse.builder().name(candidateRequest.getName()).build();
  }
}
