package com.elections.counter.controller;

import com.elections.counter.dto.request.CandidateRequest;
import com.elections.counter.dto.response.CandidateDto;
import com.elections.counter.dto.response.CandidateResponse;
import com.elections.counter.dto.response.CandidateResponseByGenre;
import com.elections.counter.dto.response.VoteDto;
import com.elections.counter.dto.response.VotesAddedResponse;
import com.elections.counter.service.CounterService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counter_api/v1")
@Slf4j
public class CounterController {

  @Autowired
  CounterService counterService;

  @PostMapping(path = "/candidate")
  public CandidateResponse createCandidate(@RequestBody CandidateRequest candidateRequest) {
    log.info(String.format("POST createCandidate [request: %s]", candidateRequest));

    return counterService.createCandidate(candidateRequest);
  }

  @PatchMapping(path = "/candidate/{id}/votes/add")
  public VotesAddedResponse addVotes(@PathVariable String id, @RequestBody VoteDto votes) {
    log.info(String.format("PATCH addVotes [id: %s, votes: %s]", id, votes));

    return counterService.addVotesToCandidate(id, votes);
  }

  @GetMapping(path = "/candidate")
  public List<CandidateDto> getCandidates() {
    log.info("GET getCandidates");

    return counterService.getAllCandidates();
  }

  @GetMapping("/candidate/:id")
  public void getCandidate(String id) {

  }

  @GetMapping(path = "/candidate/{id}/votes/genre")
  public CandidateResponseByGenre getVotesByGenre(@PathVariable String id) {
    log.info("GET getVotesByGenre [id={}]", id);

    return counterService.getCandidateVotesByGenre(id);
  }
}
