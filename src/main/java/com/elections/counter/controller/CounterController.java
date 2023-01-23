package com.elections.counter.controller;

import com.elections.counter.dto.request.CandidateRequest;
import com.elections.counter.dto.response.CandidateDto;
import com.elections.counter.dto.response.CandidateResponse;
import com.elections.counter.dto.response.VoteDto;
import com.elections.counter.dto.response.VotesAddedResponse;
import com.elections.counter.dto.response.VotesByGenreResponse;
import com.elections.counter.dto.response.VotesByPrecinctResponse;
import com.elections.counter.service.CandidateService;
import com.elections.counter.service.VoteService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counter_api/v1")
@Slf4j
public class CounterController {

  @Autowired
  CandidateService candidateService;

  @Autowired
  VoteService voteService;

  @PostMapping(path = "/candidate")
  @ResponseStatus(HttpStatus.CREATED)
  public CandidateResponse createCandidate(@RequestBody CandidateRequest candidateRequest) {
    log.info(String.format("POST createCandidate [request: %s]", candidateRequest));

    return candidateService.createCandidate(candidateRequest);
  }

  @PatchMapping(path = "/candidate/{id}/votes/add")
  public VotesAddedResponse addVotes(@PathVariable String id, @RequestBody VoteDto votes) {
    log.info(String.format("PATCH addVotes [id: %s, votes: %s]", id, votes));

    return candidateService.addVotesToCandidate(id, votes);
  }

  @GetMapping(path = "/candidate")
  public List<CandidateDto> getCandidates() {
    log.info("GET getCandidates");

    return candidateService.getAllCandidates();
  }

  @GetMapping("/candidate/:id")
  public void getCandidate(String id) {

  }

  @GetMapping(path = "/candidate/{id}/votes/genre")
  public VotesByGenreResponse getVotesByGenre(@PathVariable String id) {
    log.info("GET getVotesByGenre [id={}]", id);

    return voteService.getVotesByGenre(id);
  }

  @GetMapping("/candidate/{id}/votes/precinct")
  public List<VotesByPrecinctResponse> getVotesByPrecinct(@PathVariable String id) {
    log.info("GET getVotesByPrecinct [id={}]", id);

    return voteService.getVotesByPrecinct(id);
  }
}
